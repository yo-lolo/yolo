package com.example.myapplication.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.FriendInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : FriendTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:28
 * @UpdateRemark : 更新说明
 */
interface FriendTask {

    /**
     * 添加好友
     */
    fun insertFriend(friendInfo: FriendInfo): Long

    /**
     * 删除好友
     */
    fun deleteFriend(friendInfo: FriendInfo)

    /**
     * 获取当前帐号所有的好友
     */
    fun getFriendsById(number: Long): List<FriendInfo>

    /**
     * 获取好友信息
     */
    fun getFriendById(number: Long, friendNumber: Long): List<FriendInfo>

    /**
     * 获取该账号的好友请求列表中的好友信息
     */
    fun getNewFriendById(number: Long, friendNumber: Long): FriendInfo

    /**
     * 获取该账号的所有的好友请求
     */
    fun getAllFriendRequests(friendNumber: Long): List<FriendInfo>

    /**
     * 更新请求列表好友信息的tag
     */
    fun updateFriendTag(id: Long, tag: Int)
}