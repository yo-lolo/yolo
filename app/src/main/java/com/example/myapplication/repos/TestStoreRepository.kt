package com.example.myapplication.repos

import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.imp.UserTaskImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : UserStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/11 16:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/11 16:04
 * @UpdateRemark : 更新说明
 */
class TestStoreRepository(private val appDataBase: AppDataBase) {


    suspend fun insertUser() = withContext(Dispatchers.IO) {
        return@withContext appDataBase.userDao()
            .insertUser(User(19956596024, "123456", 75555555541))
    }


    suspend fun insertNews() = withContext(Dispatchers.IO) {
        appDataBase.NewsDao()
            .insertNews(NewsInfo(19956596024, 1, "美食", "2", "世界上最美味的旋转小火锅", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(15755949344, 1, "汽车", "2", "广汽集团裁员", "没想好"))
    }


    suspend fun insertFriend() = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao()
            .insertFriend(FriendInfo(15755949344, 19956596024, "MoMo", 22, ""))
    }


    suspend fun insertChat(chatInfo: ChatInfo) = withContext(Dispatchers.IO) {
        appDataBase.ChatDao().insertChat(chatInfo)
    }


    suspend fun getNews(): List<NewsInfo> = withContext(Dispatchers.IO) {
        return@withContext appDataBase.NewsDao().getNews()
    }

    suspend fun getFriends(number: Long): List<FriendInfo> = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao().getFriendsById(number)
    }

    suspend fun getChats(number: Long, friendNumber: Long): List<ChatInfo> =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.ChatDao().getChatsById(number, friendNumber)
        }

    suspend fun getUserById(number: Long): User =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.userDao().queryUserByNumber(number)
        }
}