package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.ChatInfo

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

    @Query("select * from ChatInfo where number = :number or :friendNumber and friendNumber = :number or :friendNumber order by time asc")
    fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo>
}