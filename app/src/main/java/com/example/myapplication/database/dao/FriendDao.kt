package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.FriendInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.dao
 * @ClassName : FriendDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:09
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:09
 * @UpdateRemark : 更新说明
 */
@Dao
interface FriendDao {
    @Insert
    fun insertFriend(friendInfo: FriendInfo): Long

    @Delete
    fun deleteFriend(friendInfo: FriendInfo)

    @Query("select * from FriendInfo where number = :number")
    fun getFriendsById(number: Long): List<FriendInfo>

    @Query("select * from FriendInfo where number = :number and friendNumber= :friendNumber")
    fun getFriendById(number: Long, friendNumber: Long): List<FriendInfo>

    @Query("select * from FriendInfo where friendNumber = :friendNumber")
    fun getAllFriendRequests(friendNumber: Long): List<FriendInfo>

    @Query("update FriendInfo set tag = :tag where id = :id")
    fun updateFriendTag(id: Long, tag: Int)
}