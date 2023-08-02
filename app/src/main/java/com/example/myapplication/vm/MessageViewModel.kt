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

    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val chatStoreRepository = DataManager.chatStoreRepository

    var friends = MutableLiveData<List<FriendInfo>>()
    var chats = MutableLiveData<List<ChatInfo>>()
    var newFriends = MutableLiveData<List<FriendInfo>>()

    var friendTag1: MutableList<FriendInfo> = mutableListOf()
    var resultMap = mutableMapOf<Long, List<String>>()
    var chatFriendsMap = MutableLiveData<Map<Long, List<String>>>()

    fun initData() {
        launchSafe {
            friends.value = friendsStoreRepository.getFriendsById(AppConfig.phoneNumber)
            // 获取到已添加成功的好友列表
            friendTag1 =
                friendsStoreRepository.getFriendsById(AppConfig.phoneNumber).filter { it.tag == 1 }
                    .toMutableList()
            newFriends.value = friendsStoreRepository.getAllFriendRequests(AppConfig.phoneNumber)
            initMess()
        }
    }

    private fun initMess() {
        launchSafe {
            // 1.获取本人的聊天记录 如果不为空 在信息展示的列表中加入本人号码
            val chatSelf = chatStoreRepository.getChatsSelf()
            val newList = friendTag1.map { it.friendNumber }.toMutableList()
            if (chatSelf.isNotEmpty()) {
                newList.add(AppConfig.phoneNumber)
            }
            // 2.在map映射中赋予resultMap的key与value key为对象的号码,value是一个List集合，包含了最后一条信息的时间和内容
            newList.map { number ->
                resultMap[number] = listOf(
                    chatStoreRepository.getLastChatBT2(number).content,
                    chatStoreRepository.getLastChatBT2(number).time.toString()
                )
            }
            // 3.通过时间将Map进行排序
            val mapSortByTime = resultMap.toList().sortedByDescending { it.second[1].toLong() }
            chatFriendsMap.value = mapSortByTime.toMap()
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