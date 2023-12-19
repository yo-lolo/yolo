package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.*
import com.example.myapplication.database.entity.*

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.dataBase.dao.entity
 * @ClassName : AppDataBase
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 11:29
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 11:29
 * @UpdateRemark : 更新说明
 */
@Database(
    entities = [User::class, FeedbackInfo::class, ChatInfo::class, CommentInfo::class, FriendInfo::class, LikeInfo::class, NewsInfo::class, NotifyInfo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun feedbackDao(): FeedbackDao
    abstract fun ChatDao(): ChatDao
    abstract fun CommentDao(): CommentDao
    abstract fun FriendDao(): FriendDao
    abstract fun LikeDao(): LikeDao
    abstract fun NewsDao(): NewsDao
    abstract fun NotifyDao(): NotifyDao
}