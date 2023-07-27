package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : MessageViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 15:27
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 15:27
 * @UpdateRemark : 更新说明
 */
class MessageViewModel : BaseViewModel() {

    private val testStoreRepository = DataManager.testStoreRepository

    var friends = MutableLiveData<List<FriendInfo>>()
    var chats = MutableLiveData<List<ChatInfo>>()

    fun initData() {
        launchSafe {
            friends.value = testStoreRepository.getFriends(AppConfig.phoneNumber)
        }
    }

    fun initChats(friendNumber: Long) {
        launchSafe {
            chats.value = testStoreRepository.getChats(AppConfig.phoneNumber, friendNumber)
        }
    }


}