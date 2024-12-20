package com.example.myapplication.shareLifeUser.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseViewModel
import com.example.myapplication.common.AppConfig
import com.example.myapplication.base.database.entity.CommentInfo
import com.example.myapplication.base.database.entity.LikeInfo
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.base.database.entity.User
import com.example.myapplication.getTag
import com.example.myapplication.base.log.SpeedyLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : NewsDetailViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 11:13
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 11:13
 * @UpdateRemark : 更新说明
 */
class NewsDetailViewModel : BaseViewModel() {

    var contentCount = MutableLiveData<Int>(0)
    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val commentStoreRepository = DataManager.commentStoreRepository
    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val likeStoreRepository = DataManager.likeStoreRepository

    var replyComment = MutableLiveData<CommentInfo>()
    var newsInfo = MutableLiveData<NewsInfo>()
    var userInfo = MutableLiveData<User>()
    var isLike = MutableLiveData<Boolean>()
    var likeInfo = MutableLiveData<LikeInfo>()
    var isFriend = MutableLiveData<Boolean>(false)
    var commentState = MutableLiveData<Boolean>(false)
    var commentsMap = MutableLiveData<Map<CommentInfo, User>>()

    /**
     * 添加好友
     */
    fun insertFriend(newsId: Long) {
        SpeedyLog.d(getTag(),"insertFriend >>> 添加好友")
        viewModelScope.launch {
            kotlin.runCatching {
                val info = newsStoreRepository.getNewsById(newsId)
                friendsStoreRepository.insertFriend(info.number)
            }.onSuccess {
                SpeedyLog.d(getTag(),"<<< 已发送好友请求,等待好友验证")
                ToastUtils.showLong("已发送好友请求,等待好友验证")
            }
            initData(newsId)
        }
    }

    /**
     * 初始化数据（评论列表，好友状态）
     */
    fun initData(newsId: Long) {
        viewModelScope.launch {
            val news = newsStoreRepository.getNewsById(newsId)
            newsInfo.value = news
            userInfo.value = userStoreRepository.queryUserByNumber(news.number)
            if (AppConfig.phoneNumber == newsInfo.value!!.number) {
                isFriend.value = true
            }
            val friend = friendsStoreRepository.getFriendById(newsInfo.value!!.number)

            if (friend != null)
                if (friend.tag == AppConfig.IS_FRIEND) {
                    isFriend.value = true
                }
            initComment(newsId)
            initLike(newsId)

        }
    }


    /**
     * 初始化所有评论
     */
    private fun initComment(newsId: Long) {

        launchTest {
            // 获取该文章的所有评论
            val comments = commentStoreRepository.getCommentsByNewId(newsId)
            // 获取level为1、2的评论、按时间进行排序
            val level1 = comments.filter { it.level == 1 }.sortedBy { it.time }
            val level2 = comments.filter { it.level == 2 }.sortedBy { it.time }
            // 将level为2的列表按照回复评论的Id进行分组
            val level2Group = level2.groupBy { it.replyId }
            // 创建空列表
            val list = mutableListOf<CommentInfo>()
            // 遍历level1的列表 获取到对应的level2列表 依次添加进空列表中
            level1.forEach { level1Info ->
                val newLevel2Group = level2Group[level1Info.id]
                list.add(level1Info)
                if (newLevel2Group != null) {
                    list.addAll(newLevel2Group)
                }
            }
            val resultMap = mutableMapOf<CommentInfo, User>()
            comments.map {
                val user = userStoreRepository.queryUserByNumber(it.number)
                resultMap[it] = user
            }
            commentsMap.value = resultMap.toMap()
            // 解决level1的评论删除后 level2的评论不删除时 显示评论数目与所看到的不同的问题
            val commentLevel1 = comments.filter { it.level == 1 }
            val commentLevel2 = comments.filter { it.level == 2 }
            val replyIdListExist = commentLevel1.map { it.id }
            val replyCounts = commentLevel2.filter { it.replyId in replyIdListExist }.size
            contentCount.value = commentLevel1.size + replyCounts
        }
    }

    /**
     * 初始化点赞
     */
    private fun initLike(newsId: Long) {
        viewModelScope.launch {
            val likes = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                .filter { it.newsId == newsId }
            if (likes.isNotEmpty())
                likeInfo.value = likes[0]
            isLike.value = likes.isNotEmpty()
        }
    }


    fun initReplyComment(commentId: Long) {
        viewModelScope.launch {
            replyComment.value = commentStoreRepository.getCommentsById(commentId)
        }
    }

    /**
     * 发布评论
     */
    fun postComment(
        newsId: Long,
        content: String,
        level: Int,
        replyNumber: Long? = null,
        replyId: Long? = null
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                commentStoreRepository.insertComment(
                    newsId,
                    content,
                    level,
                    replyNumber,
                    replyId
                )
            }.onSuccess {
                ToastUtils.showShort("评论发布成功")
                delay(1000)
                commentState.value = true
            }.onFailure {
                ToastUtils.showShort("评论发布失败,请重试")
                SpeedyLog.d(getTag(), it.toString())
            }
        }
    }

    /**
     * 评论删除逻辑
     */
    fun deleteComment(newsId: Long, commentInfo: CommentInfo) {
        SpeedyLog.d(getTag(),"deleteComment >>> 评论删除")
        if (AppConfig.phoneNumber == commentInfo.number || commentInfo.newsId == newsId) {
            viewModelScope.launch {
                kotlin.runCatching {
                    commentStoreRepository.deleteCommentById(commentInfo.id)
                }.onSuccess {
                    SpeedyLog.d(getTag(), "<<< 删除评论成功")
                    ToastUtils.showShort("删除评论成功")
                    initData(newsId) //更新数据
                }.onFailure {
                    SpeedyLog.d(getTag(), "<<< 删除评论失败")
                    ToastUtils.showShort("删除评论失败,请重试")
                }
            }
        }
    }

    /**
     * 根据点赞状态判断插入和删除
     */
    fun chargeLike(newsId: Long) {
        viewModelScope.launch {
            val newInfo = newsStoreRepository.getNewsById(newsId)
            if (isLike.value!!) {
                deleteLike(newsId)
            } else {
                postLike(newInfo)
            }
        }

    }

    /**
     * 删除点赞记录
     */
    private fun deleteLike(newsId: Long) {
        viewModelScope.launch {
            likeStoreRepository.deleteLikeById(likeInfo.value!!.id)
            initLike(newsId)
        }
    }

    /**
     * 插入点赞记录
     */
    private fun postLike(newInfo: NewsInfo) {
        viewModelScope.launch {
            likeStoreRepository.insertLike(
                LikeInfo(
                    AppConfig.phoneNumber,
                    newInfo.id,
                    newInfo.number
                )
            )
            initLike(newInfo.id)
        }
    }


}