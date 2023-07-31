package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.util.TimeUtil
import java.util.Comparator

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
    var chatFriends = MutableLiveData<List<ChatInfo>>()
    var newFriends = MutableLiveData<List<FriendInfo>>()

    fun initData() {
        launchSafe {
            friends.value = testStoreRepository.getFriends(AppConfig.phoneNumber)
            newFriends.value = testStoreRepository.getAllFriendRequests(AppConfig.phoneNumber)
            chatFriends.value = testStoreRepository.getChatFriends(AppConfig.phoneNumber)
        }
    }

    fun initChats(friendNumber: Long) {
        launchSafe {
            chats.value = testStoreRepository.getChats(AppConfig.phoneNumber, friendNumber)
        }
    }

    fun insertChat(friendNumber: Long, content: String) {
        launchSafe {
            testStoreRepository.insertChat(
                ChatInfo(
                    AppConfig.phoneNumber,
                    friendNumber,
                    AppConfig.phoneNumber,
                    TimeUtil.getCurrentMill(),
                    content
                )
            )
            initChats(friendNumber)
        }
    }

    /**
     * 同意好友申请
     * 1.将好友申请的tag更新为1
     * 2.添加好友
     */
    fun agreeFriend(id: Long, number: Long) {
        launchSafe {
            testStoreRepository.insertFriend(number, 1)
            testStoreRepository.updateFriendTag(id, 1)
            onRefresh()
        }
    }

    private fun onRefresh() {
        initData()
    }


}