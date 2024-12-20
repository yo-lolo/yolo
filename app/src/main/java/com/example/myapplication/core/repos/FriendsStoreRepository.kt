package com.example.myapplication.core.repos

import com.example.myapplication.common.AppConfig
import com.example.myapplication.base.database.entity.FriendInfo
import com.example.myapplication.core.imp.FriendTaskImp
import com.example.myapplication.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : FriendsStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:49
 * @UpdateRemark : 更新说明
 */
class FriendsStoreRepository(private val friendTaskImp: FriendTaskImp) {


    /**
     * 添加好友
     */
    suspend fun insertFriend(authorNumber: Long, tag: Int = 0): Long = withContext(Dispatchers.IO) {
        return@withContext friendTaskImp.insertFriend(
            FriendInfo(
                AppConfig.phoneNumber,
                authorNumber,
                TimeUtil.getCurrentMill(),
                "",
                tag
            )
        )
    }

    /**
     * 删除好友
     */
    suspend fun deleteFriend(friendNumber: Long) = withContext(Dispatchers.IO) {
        return@withContext friendTaskImp.deleteFriend(AppConfig.phoneNumber, friendNumber)
    }

    /**
     * 获取当前帐号所有的好友
     */
    suspend fun getFriendsById(number: Long): List<FriendInfo> = withContext(Dispatchers.IO) {
        return@withContext friendTaskImp.getFriendsById(number)
    }

    /**
     * 获取好友信息
     */
    suspend fun getFriendById(friendNumber: Long): FriendInfo =
        withContext(Dispatchers.IO) {
            return@withContext friendTaskImp.getFriendById(AppConfig.phoneNumber, friendNumber)
        }

    /**
     * 获取该账号的好友请求列表中的好友信息
     */
    suspend fun getNewFriendById(number: Long, friendNumber: Long): FriendInfo =
        withContext(Dispatchers.IO) {
            return@withContext friendTaskImp.getNewFriendById(number, friendNumber)
        }

    /**
     * 获取该账号的所有的好友请求
     */
    suspend fun getAllFriendRequests(friendNumber: Long): List<FriendInfo> =
        withContext(Dispatchers.IO) {
            return@withContext friendTaskImp.getAllFriendRequests(friendNumber)
        }

    /**
     * 更新请求列表好友信息的tag
     */
    suspend fun updateFriendTag(id: Long, tag: Int) = withContext(Dispatchers.IO) {
        return@withContext friendTaskImp.updateFriendTag(id, tag)
    }

    /**
     * 更新好友置顶状态
     */
    suspend fun updateFriendTopState(friendNumber: Long, isTop: Boolean) =
        withContext(Dispatchers.IO) {
            return@withContext friendTaskImp.updateFriendTopState(
                AppConfig.phoneNumber,
                friendNumber,
                isTop
            )
        }

    /**
     * 更新好友通知状态
     */
    suspend fun updateFriendNotifyState(friendNumber: Long, isNotify: Boolean) =
        withContext(Dispatchers.IO) {
            return@withContext friendTaskImp.updateFriendNotifyState(
                AppConfig.phoneNumber,
                friendNumber,
                isNotify
            )
        }

}