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

    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val chatStoreRepository = DataManager.chatStoreRepository

    var friends = MutableLiveData<List<FriendInfo>>()
    var chats = MutableLiveData<List<ChatInfo>>()
    var chatFriends = MutableLiveData<List<ChatInfo>>()
    var newFriends = MutableLiveData<List<FriendInfo>>()

    fun initData() {
        launchSafe {
            friends.value = friendsStoreRepository.getFriendsById(AppConfig.phoneNumber)
            newFriends.value = friendsStoreRepository.getAllFriendRequests(AppConfig.phoneNumber)
            chatFriends.value = chatStoreRepository.getChatFriends(AppConfig.phoneNumber)
        }
    }

    fun initChats(friendNumber: Long) {
        launchSafe {
            // fix:给自己发送消息时的逻辑问题
            if (AppConfig.phoneNumber != friendNumber) {
                chats.value = chatStoreRepository.getChatsById(AppConfig.phoneNumber, friendNumber)
                    .filter { it.friendNumber != it.number }
            } else {
                chats.value = chatStoreRepository.getChatsById(AppConfig.phoneNumber, friendNumber)
            }
        }
    }

    fun insertChat(friendNumber: Long, content: String) {
        launchSafe {
            chatStoreRepository.insertChat(friendNumber, content)
            initChats(friendNumber)
        }
    }

    /**
     * 同意好友申请
     * 1.将好友申请的tag更新为1
     * 2.添加好友
     * 3.添加聊天 "我通过了你的朋友验证请求，现在我们可以开始聊天了"
     */
    fun agreeFriend(id: Long, number: Long) {
        launchSafe {
            friendsStoreRepository.insertFriend(number, 1)
            friendsStoreRepository.updateFriendTag(id, 1)
            chatStoreRepository.insertChat(number, "我通过了你的朋友验证请求，现在我们可以开始聊天了")
            onRefresh(number)
        }
    }

    private fun onRefresh(friendNumber: Long) {
        initData()
        initChats(friendNumber)
    }


}