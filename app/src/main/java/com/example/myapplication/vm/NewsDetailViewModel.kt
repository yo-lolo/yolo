package com.example.myapplication.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.MessData
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.LikeInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User
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
        launchSafe {
            kotlin.runCatching {
                val info = newsStoreRepository.getNewsById(newsId)
                friendsStoreRepository.insertFriend(info.number)
            }.onSuccess {
                ToastUtils.showLong("已发送好友请求,等待好友验证")
            }
            initData(newsId)
        }
    }

    /**
     * 初始化数据（评论列表，好友状态）
     */
    fun initData(newsId: Long) {
        launchSafe {
            val news = newsStoreRepository.getNewsById(newsId)
            newsInfo.value = news
            userInfo.value = userStoreRepository.queryUserByNumber(news.number)
            if (AppConfig.phoneNumber == newsInfo.value!!.number) {
                isFriend.value = true
            }
            val friend = friendsStoreRepository.getFriendById(newsInfo.value!!.number)

            if (friend.isNotEmpty()) {
                if (friend[0].tag == 1) {
                    isFriend.value = true
                }
            }

            initComment(newsId)
            initLike(newsId)

        }
    }

    fun initComment(newsId: Long) {
        viewModelScope.launch {
            val comments = commentStoreRepository.getCommentsByNewId(newsId)
            val resultMap = mutableMapOf<CommentInfo, User>()
            comments.map {
                val user = userStoreRepository.queryUserByNumber(it.number)
                resultMap[it] = user
            }
            commentsMap.value = resultMap.toMap()
            // 解决level1的评论删除后 level2的评论不删除时 显示评论数目与所看到的不同的问题
            val commentLevel1 = comments.filter { it.level == 1 }
            val commentLevel2 = comments.filter { it.level == 2 }
            var replyIdListExist = commentLevel1.map { it.id }
            val replyCounts = commentLevel2.filter { it.replyId in replyIdListExist }.size
            contentCount.value = commentLevel1.size + replyCounts
        }
    }

    fun initLike(newsId: Long) {
        viewModelScope.launch {
            val ss = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                .filter { it.newsId == newsId }
            if (ss.isNotEmpty())
                likeInfo.value = ss[0]
            isLike.value = ss.isNotEmpty()
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
                Log.e("yolo", it.toString())
            }
        }
    }

    /**
     * 评论删除逻辑
     */
    fun deleteComment(newsId: Long, commentInfo: CommentInfo) {
        if (AppConfig.phoneNumber == commentInfo.number || commentInfo.newsId == newsId) {
            viewModelScope.launch {
                kotlin.runCatching {
                    commentStoreRepository.deleteCommentById(commentInfo.id)
                }.onSuccess {
                    ToastUtils.showShort("删除评论成功")
                    initData(newsId) //更新数据
                }.onFailure {
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