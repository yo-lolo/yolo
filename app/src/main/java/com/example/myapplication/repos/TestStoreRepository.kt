package com.example.myapplication.repos

import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.imp.UserTaskImp
import com.example.myapplication.util.TimeUtil
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
        appDataBase.userDao()
            .insertUser(User(11111111111, "123456", 1690789826637))
        appDataBase.userDao()
            .insertUser(User(22222222222, "123456", 1690789826637))
    }


    suspend fun insertNews() = withContext(Dispatchers.IO) {
        appDataBase.NewsDao()
            .insertNews(NewsInfo(19956596024, 1, "美食", "2", "世界上最美味的旋转小火锅！", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(15755949344, 1, "汽车", "2", "广汽集团", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(11111111111, 1690789826637, "支付宝", "2", "支付宝额度提升啦！", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(22222222222, 1690789826637, "微信", "2", "微信闪退怎么回事？", "没想好"))
    }


    suspend fun insertFriend(authorNumber: Long, tag: Int = 0) = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao()
            .insertFriend(
                FriendInfo(
                    AppConfig.phoneNumber,
                    authorNumber,
                    TimeUtil.getCurrentMill(),
                    "",
                    tag
                )
            )
    }

    suspend fun updateFriendTag(id: Long, tag: Int) = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao().updateFriendTag(id, tag)
    }


    suspend fun insertChat(friendNumber: Long, content: String) = withContext(Dispatchers.IO) {
        appDataBase.ChatDao().insertChat(
            ChatInfo(
                AppConfig.phoneNumber,
                friendNumber,
                AppConfig.phoneNumber,
                TimeUtil.getCurrentMill(),
                content
            )
        )
    }


    suspend fun getNews(): List<NewsInfo> = withContext(Dispatchers.IO) {
        return@withContext appDataBase.NewsDao().getNews()
    }

    /**
     * 获取当前帐号所有的好友
     */
    suspend fun getFriends(number: Long): List<FriendInfo> = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao().getFriendsById(number)
    }

    suspend fun getFriendById(authorNumber: Long): List<FriendInfo> = withContext(Dispatchers.IO) {
        return@withContext appDataBase.FriendDao()
            .getFriendById(AppConfig.phoneNumber, authorNumber)
    }

    /**
     * 获取该账号的所有的好友请求
     *
     */
    suspend fun getAllFriendRequests(number: Long): List<FriendInfo> =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.FriendDao().getAllFriendRequests(number)
        }

    /**
     * 获取该账号的好友请求列表中的好友信息
     */
    suspend fun getNewFriendById(nFN: Long): FriendInfo =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.FriendDao()
                .getNewFriendById(AppConfig.phoneNumber, nFN)
        }

    /**
     * 获取两个账号之间的聊天记录
     */
    suspend fun getChatsById(number: Long, friendNumber: Long): List<ChatInfo> =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.ChatDao().getChatsById(number, friendNumber)
        }

    /**
     * 获取该帐号所有的聊天好友
     */
    suspend fun getChatFriends(number: Long): List<ChatInfo> =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.ChatDao().getChatFriends(number)
        }

    suspend fun getUserById(number: Long): User =
        withContext(Dispatchers.IO) {
            return@withContext appDataBase.userDao().queryUserByNumber(number)
        }


}