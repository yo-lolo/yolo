package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig

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

    private val testStoreRepository = DataManager.testStoreRepository
    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val chatStoreRepository = DataManager.chatStoreRepository
    var isFriend = MutableLiveData<Boolean>(false)

    fun insertFriend(authorNumber: Long) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.insertFriend(authorNumber)
            }.onSuccess {
                ToastUtils.showShort("等待好友验证")
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
                isFriend.value = true
            }
        }
    }


}