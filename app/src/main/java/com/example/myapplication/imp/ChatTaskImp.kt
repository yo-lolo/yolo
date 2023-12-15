package com.example.myapplication.imp

import com.example.myapplication.api.ChatTask
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.dao.ChatDao
import com.example.myapplication.database.entity.ChatInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : ChatTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:30
 * @UpdateRemark : 更新说明
 */
class ChatTaskImp(
    private val appDataBase: AppDataBase,
    private val chatDao: ChatDao = appDataBase.ChatDao()
) : ChatTask {

    override fun insertChat(chatInfo: ChatInfo): Long {
        return chatDao.insertChat(chatInfo)
    }

    override fun deleteChat(chatInfo: ChatInfo) {
        chatDao.deleteChat(chatInfo)
    }

    override fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo> {
        return chatDao.getChatsById(number, friendNumber)
    }

    override fun getChatFriends(number: Long): List<ChatInfo> {
        return chatDao.getChatFriends(number)
    }

    override fun getLastChatBT2(number: Long, friendNumber: Long): ChatInfo {
        return chatDao.getLastChatBT2(number, friendNumber)
    }

    override fun getChatsSelf(phoneNumber: Long): List<ChatInfo> {
        return chatDao.getChatsSelf(phoneNumber)
    }

    override fun clearChats(number: Long, friendNumber: Long) {
        return chatDao.clearChats(number, friendNumber)
    }

}