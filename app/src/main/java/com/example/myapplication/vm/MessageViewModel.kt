package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.ChatData
import com.example.myapplication.data.MessData
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.User

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
    private val userStoreRepository = DataManager.userStoreRepository
    var mineFriendsMap = MutableLiveData<Map<FriendInfo, User>>()
    var chatsMap = MutableLiveData<Map<ChatInfo, ChatData>>()
    var newFriendsMap = MutableLiveData<Map<FriendInfo, User>>()
    var friendUserInfo = MutableLiveData<User>()
    var chatFriendsMap = MutableLiveData<Map<Long, MessData>>()
    var isRot = MutableLiveData<Boolean>()

    fun initFriendsData() {
        launchSafe {
            val mineFriends = friendsStoreRepository.getFriendsById(AppConfig.phoneNumber)
            val resultMap = mutableMapOf<FriendInfo, User>()
            mineFriends.map {
                val user = userStoreRepository.queryUserByNumber(it.friendNumber)
                resultMap[it] = user
            }
            mineFriendsMap.value = resultMap.toMap()
            initMess()
        }
    }

    /**
     * 初始化新朋友
     */
    fun initNewFriends() {
        launchSafe {
            val newFriends = friendsStoreRepository.getAllFriendRequests(AppConfig.phoneNumber)
            val resultMap = mutableMapOf<FriendInfo, User>()
            newFriends.map {
                val user = userStoreRepository.queryUserByNumber(it.number)
                resultMap[it] = user
            }
            newFriendsMap.value = resultMap.toMap()
            isRot.value = newFriends.any { it.tag == 0 }
        }
    }

    /**
     * 初始化聊天记录
     */
    fun initMess() {
        launchSafe {
            val resultMap = mutableMapOf<Long, MessData>()
            // 获取到已添加成功的好友列表
            val friendTag1 =
                friendsStoreRepository.getFriendsById(AppConfig.phoneNumber).filter { it.tag == 1 }
                    .toMutableList()
            // 1.获取本人的聊天记录 如果不为空 在信息展示的列表中加入本人号码
            val chatSelf = chatStoreRepository.getChatsSelf()
            val newList = friendTag1.map { it.friendNumber }.toMutableList()
            if (chatSelf.isNotEmpty()) {
                newList.add(AppConfig.phoneNumber)
            }
            // 2.在map映射中赋予resultMap的key与value key为对象的号码,value是一个List集合，包含了最后一条信息的时间和内容
            newList.map { number ->
                val chatInfo = chatStoreRepository.getLastChatBT2(number)
                val friendUser = userStoreRepository.queryUserByNumber(number)
                resultMap[number] = MessData(chatInfo, friendUser)
            }
            // 3.通过时间将Map进行排序
            val mapSortByTime = resultMap.toList().sortedByDescending { it.second.newChatInfo.time }
            chatFriendsMap.value = mapSortByTime.toMap()
        }
    }

    fun initChats(friendNumber: Long) {
        launchSafe {
            friendUserInfo.value = userStoreRepository.queryUserByNumber(friendNumber)
            // fix:给自己发送消息时的逻辑问题
            val resultMap = mutableMapOf<ChatInfo, ChatData>()
            val chats = if (AppConfig.phoneNumber != friendNumber) {
                chatStoreRepository.getChatsById(AppConfig.phoneNumber, friendNumber)
                    .filter { it.friendNumber != it.number }
            } else {
                chatStoreRepository.getChatsById(AppConfig.phoneNumber, friendNumber)
            }
            chats.map { chatInfo ->
                val me = userStoreRepository.queryUserByNumber(AppConfig.phoneNumber)
                val friend = userStoreRepository.queryUserByNumber(friendNumber)
                resultMap[chatInfo] = ChatData(me, friend)
            }
            chatsMap.value = resultMap.toMap()
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
        initFriendsData()
        initNewFriends()
        initChats(friendNumber)
    }


}