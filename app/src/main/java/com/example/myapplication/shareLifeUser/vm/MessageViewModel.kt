package com.example.myapplication.shareLifeUser.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseViewModel
import com.example.myapplication.common.AppConfig
import com.example.myapplication.bean.ChatData
import com.example.myapplication.bean.MessData
import com.example.myapplication.base.database.entity.ChatInfo
import com.example.myapplication.base.database.entity.FriendInfo
import com.example.myapplication.base.database.entity.User
import kotlinx.coroutines.delay

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
    var chatFriendsMap = MutableLiveData<Map<Long, MessData>>()

    var friendUserInfo = MutableLiveData<User>()
    var isRot = MutableLiveData<Boolean>()
    var friendInfo = MutableLiveData<FriendInfo>()
    var deleteFriendState = MutableLiveData<Boolean>(false)

    fun initFriendsData() {
        launchSafe {
            val mineFriends = friendsStoreRepository.getFriendsById(AppConfig.account)
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
            val newFriends = friendsStoreRepository.getAllFriendRequests(AppConfig.account)
            val resultMap = mutableMapOf<FriendInfo, User>()
            newFriends.map {
                val user = userStoreRepository.queryUserByNumber(it.number)
                resultMap[it] = user
            }
            newFriendsMap.value = resultMap.toMap()
            isRot.value = newFriends.any { it.tag == AppConfig.NOT_FRIEND }
        }
    }

    /**
     * 初始化聊天记录
     */
    fun initMess() {
        launchSafe {
            val resultMap = mutableMapOf<Long, MessData>()
            // 1、获取到已添加成功的好友列表
            val friendTag1 =
                friendsStoreRepository.getFriendsById(AppConfig.account)
                    .filter { it.tag == AppConfig.IS_FRIEND }
                    .toMutableList()
            // 2、获取本人的聊天记录 如果不为空 在信息展示的列表中加入本人号码
            val chatSelf = chatStoreRepository.getChatsSelf()
            val newList = friendTag1.map { it.friendNumber }.toMutableList()
            if (chatSelf.isNotEmpty()) {
                newList.add(AppConfig.account)
            }
            // 3、在map映射中赋予resultMap的key与value key为对象的号码,value是一个List集合，包含了最后一条信息的时间和内容
            newList.map { number ->
                val chatInfo = chatStoreRepository.getLastChatBT2(number)
                val friendUser = userStoreRepository.queryUserByNumber(number)
                resultMap[number] = MessData(chatInfo, friendUser)
            }
            // 4、通过时间将Map进行排序
            val mapSortByTime = resultMap.toList().sortedByDescending { it.second.newChatInfo.time }
            chatFriendsMap.value = mapSortByTime.toMap()
        }
    }

    fun initChats(receiver: Long) {
        launchSafe {
            friendInfo.value = friendsStoreRepository.getFriendById(receiver)
            friendUserInfo.value = userStoreRepository.queryUserByNumber(receiver)
            // fix:给自己发送消息时的逻辑问题
            val resultMap = mutableMapOf<ChatInfo, ChatData>()
            val chats = if (AppConfig.account != receiver) {
                chatStoreRepository.getChatsById(AppConfig.account, receiver)
                    .filter { it.receiver != it.sender }
            } else {
                chatStoreRepository.getChatsById(AppConfig.account, receiver)
            }
            chats.map { chatInfo ->
                val me = userStoreRepository.queryUserByNumber(AppConfig.account)
                val friend = userStoreRepository.queryUserByNumber(receiver)
                resultMap[chatInfo] = ChatData(me, friend)
            }
            chatsMap.value = resultMap.toMap()
        }
    }

    /**
     * 发送聊天
     */
    fun insertChat(receiver: Long, content: String) {
        launchSafe {
            chatStoreRepository.insertChat(receiver, content)
            initChats(receiver)
        }
    }

    /**
     * 同意好友申请
     * 1.将好友申请的tag更新为 AppConfig.IS_FRIEND
     * 2.添加好友
     * 3.添加聊天 "我通过了你的朋友验证请求，现在我们可以开始聊天了"
     */
    fun agreeFriend(id: Long, number: Long) {
        launchSafe {
            friendsStoreRepository.insertFriend(number, AppConfig.IS_FRIEND)
            friendsStoreRepository.updateFriendTag(id, AppConfig.IS_FRIEND)
            chatStoreRepository.insertChat(
                number,
                "我通过了你的朋友验证请求，现在我们可以开始聊天了"
            )
            onRefresh(number)
        }
    }

    private fun onRefresh(receiver: Long) {
        initFriendsData()
        initNewFriends()
        initChats(receiver)
    }

    /**
     * 删除好友
     * 两边都要删除好友和聊天记录
     */
    fun deleteFriend(friendNumber: Long) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.deleteFriend(friendNumber)
                chatStoreRepository.clearChats(friendNumber)
            }.onFailure {
                ToastUtils.showShort("删除好友失败")
            }.onSuccess {
                ToastUtils.showShort("删除好友成功")
                delay(1000)
                deleteFriendState.value = true
            }
        }
    }

    /**
     * 更新好友置顶状态
     */
    fun updateFriendTopState(friendNumber: Long, isTop: Boolean) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.updateFriendTopState(friendNumber, isTop)
            }.onFailure {
                ToastUtils.showShort("置顶状态设置失败，请重试")
            }
        }
    }

    /**
     * 清除聊天记录
     */
    fun clearChats(friendNumber: Long) {
        //todo 清除聊天记录
        ToastUtils.showShort("清除聊天记录")
    }

    /**
     * 更新好友通知状态
     */
    fun updateFriendNotifyState(friendNumber: Long, isNotify: Boolean) {
        launchSafe {
            kotlin.runCatching {
                friendsStoreRepository.updateFriendNotifyState(friendNumber, isNotify)
            }.onFailure {
                ToastUtils.showShort("通知状态设置失败，请重试")
            }
        }

    }


}