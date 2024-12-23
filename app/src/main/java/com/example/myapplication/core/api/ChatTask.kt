package com.example.myapplication.core.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.ChatInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : ChatTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:28
 * @UpdateRemark : 更新说明
 */
interface ChatTask {

    /**
     * 添加聊天
     */
    fun insertChat(chatInfo: ChatInfo): Long

    /**
     * 删除聊天
     */
    fun deleteChat(chatInfo: ChatInfo)

    /**
     * 获取两个账号之间的聊天记录
     */
    fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo>

    /**
     * 获取该帐号所有的聊天好友
     */
    fun getChatFriends(number: Long): List<ChatInfo>

    /**
     * 获取两个账号之间的最后一条记录
     */
    fun getLastChatBT2(number: Long, friendNumber: Long): ChatInfo

    /**
     * 获取自己的的聊天记录
     */
    fun getChatsSelf(phoneNumber: Long): List<ChatInfo>

    /**
     * 清空两个账号之间的所有记录
     */
    fun clearChats(number: Long, friendNumber: Long)
}