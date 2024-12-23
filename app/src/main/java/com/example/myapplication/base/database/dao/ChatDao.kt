package com.example.myapplication.base.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.ChatInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.dao
 * @ClassName : ChatDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:08
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:08
 * @UpdateRemark : 更新说明
 */
@Dao
interface ChatDao {

    @Insert
    fun insertChat(chatInfo: ChatInfo): Long

    @Delete
    fun deleteChat(chatInfo: ChatInfo)

    @Query("select * from ChatInfo where sender in (:number,:friendNumber) and receiver in (:number,:friendNumber) order by time asc")
    fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo>

    @Query("select * from ChatInfo where (sender = :number and receiver =:friendNumber) or sender = :friendNumber and receiver =:number order by time desc limit 1")
    fun getLastChatBT2(number: Long, friendNumber: Long): ChatInfo

    @Query("select * from ChatInfo where sender = :number group by receiver")
    fun getChatFriends(number: Long): List<ChatInfo>

    @Query("select * from ChatInfo where sender = :phoneNumber and receiver = :phoneNumber")
    fun getChatsSelf(phoneNumber: Long): List<ChatInfo>

    @Query("delete from ChatInfo where sender = :number and receiver=:friendNumber or sender = :friendNumber and receiver = :number")
    fun clearChats(number: Long, friendNumber: Long)
}