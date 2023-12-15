package com.example.myapplication.repos

import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.imp.ChatTaskImp
import com.example.myapplication.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : ChatStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:48
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:48
 * @UpdateRemark : 更新说明
 */
class ChatStoreRepository(private val chatTaskImp: ChatTaskImp) {

    /**
     * 添加聊天
     */
    suspend fun insertChat(friendNumber: Long, content: String): Long =
        withContext(Dispatchers.IO) {
            return@withContext chatTaskImp.insertChat(
                ChatInfo(
                    AppConfig.phoneNumber,
                    friendNumber,
                    AppConfig.phoneNumber,
                    TimeUtil.getCurrentMill(),
                    content
                )
            )
        }

    /**
     * 删除聊天
     */
    suspend fun deleteChat(chatInfo: ChatInfo) = withContext(Dispatchers.IO) {
        return@withContext chatTaskImp.deleteChat(chatInfo)
    }

    /**
     * 获取两个账号之间的聊天记录
     */
    suspend fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo> =
        withContext(Dispatchers.IO) {
            return@withContext chatTaskImp.getChatsById(number, friendNumber)
        }

    /**
     * 获取自己的的聊天记录
     */
    suspend fun getChatsSelf(): List<ChatInfo> =
        withContext(Dispatchers.IO) {
            return@withContext chatTaskImp.getChatsSelf(AppConfig.phoneNumber)
        }

    /**
     * 获取该帐号所有的聊天好友
     */
    suspend fun getChatFriends(number: Long): List<ChatInfo> = withContext(Dispatchers.IO) {
        return@withContext chatTaskImp.getChatFriends(number)
    }

    /**
     * 获取两个账号之间的最后一条记录
     */
    suspend fun getLastChatBT2(number: Long): ChatInfo = withContext(Dispatchers.IO) {
        return@withContext chatTaskImp.getLastChatBT2(number, AppConfig.phoneNumber)
    }

    /**
     * 清空两个账号之间的所有记录
     */
    suspend fun clearChats(friendNumber: Long) = withContext(Dispatchers.IO) {
        return@withContext chatTaskImp.clearChats(AppConfig.phoneNumber, friendNumber)
    }

}