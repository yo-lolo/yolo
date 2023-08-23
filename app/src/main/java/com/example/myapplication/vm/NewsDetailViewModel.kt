package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.CommentInfo
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

    fun insertFriend(authorNumber: Long) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.insertFriend(authorNumber)
            }.onSuccess {
                ToastUtils.showLong("已发送好友请求,等待好友验证")
            }
            initData(authorNumber)
        }
    }

    fun initData(authorNumber: Long) {
        launchSafe {
            if (AppConfig.phoneNumber == authorNumber) {
                isFriend.value = true
            }
            val friend = friendsStoreRepository.getFriendById(authorNumber)
            if (friend.isNotEmpty()) {
                if (friend[0].tag == 1) {
                    isFriend.value = true
                }
            }
        }
    }

    fun initComments(newsId: Long) {
        viewModelScope.launch {
            comments.value = commentStoreRepository.getCommentsByNewId(newsId)
        }
    }

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


}