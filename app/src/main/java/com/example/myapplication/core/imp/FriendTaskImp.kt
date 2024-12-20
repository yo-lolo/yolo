package com.example.myapplication.core.imp

import com.example.myapplication.core.api.FriendTask
import com.example.myapplication.base.database.AppDataBase
import com.example.myapplication.base.database.dao.FriendDao
import com.example.myapplication.base.database.entity.FriendInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : FriendTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:30
 * @UpdateRemark : 更新说明
 */
class FriendTaskImp(
    private val appDataBase: AppDataBase,
    private val friendDao: FriendDao = appDataBase.FriendDao()
) : FriendTask {

    override fun insertFriend(friendInfo: FriendInfo): Long {
        return friendDao.insertFriend(friendInfo)
    }

    override fun deleteFriend(number: Long, friendNumber: Long) {
        friendDao.deleteFriend(number, friendNumber)
    }

    override fun getFriendsById(number: Long): List<FriendInfo> {
        return friendDao.getFriendsById(number)
    }

    override fun getFriendById(number: Long, friendNumber: Long): FriendInfo {
        return friendDao.getFriendById(number, friendNumber)
    }

    override fun getNewFriendById(number: Long, friendNumber: Long): FriendInfo {
        return friendDao.getNewFriendById(number, friendNumber)
    }

    override fun getAllFriendRequests(friendNumber: Long): List<FriendInfo> {
        return friendDao.getAllFriendRequests(friendNumber)
    }

    override fun updateFriendTag(id: Long, tag: Int) {
        friendDao.updateFriendTag(id, tag)
    }

    override fun updateFriendTopState(number: Long, friendNumber: Long, isTop: Boolean) {
        friendDao.updateFriendTopState(number, friendNumber, isTop)
    }

    override fun updateFriendNotifyState(number: Long, friendNumber: Long, isNotify: Boolean) {
        friendDao.updateFriendNotifyState(number, friendNumber, isNotify)
    }
}