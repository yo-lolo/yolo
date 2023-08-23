package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.NewsInfo
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

    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val commentStoreRepository = DataManager.commentStoreRepository
    var isFriend = MutableLiveData<Boolean>(false)
    var commentState = MutableLiveData<Boolean>(false)
    var comments = MutableLiveData<List<CommentInfo>>()

    /**
     * 添加好友
     */
    fun insertFriend(newsInfo: NewsInfo) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.insertFriend(newsInfo.number)
            }.onSuccess {
                ToastUtils.showLong("已发送好友请求,等待好友验证")
            }
            initData(newsInfo)
        }
    }

    /**
     * 初始化数据（评论列表，好友状态）
     */
    fun initData(newsInfo: NewsInfo) {
        launchSafe {
            comments.value = commentStoreRepository.getCommentsByNewId(newsInfo.id)
            if (AppConfig.phoneNumber == newsInfo.number) {
                isFriend.value = true
            }
            val friend = friendsStoreRepository.getFriendById(newsInfo.number)

            if (friend.isNotEmpty()) {
                if (friend[0].tag == 1) {
                    isFriend.value = true
                }
            }
        }
    }

    /**
     * 发布评论
     */
    fun postComment(newsId: Long, content: String, level: Int, replyId: Long? = null) {
        viewModelScope.launch {
            kotlin.runCatching {
                commentStoreRepository.insertComment(newsId, content, level, replyId)
            }.onSuccess {
                ToastUtils.showShort("评论发布成功")
                delay(1000)
                commentState.value = true
            }.onFailure {
                ToastUtils.showShort("评论发布失败,请重试")
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
                }.onFailure {
                    ToastUtils.showShort("删除评论失败,请重试")
                }
            }
        }
    }


}