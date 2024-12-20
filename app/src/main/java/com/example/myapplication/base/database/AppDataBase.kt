package com.example.myapplication.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.base.database.dao.ChatDao
import com.example.myapplication.base.database.dao.CommentDao
import com.example.myapplication.base.database.dao.FeedbackDao
import com.example.myapplication.base.database.dao.FriendDao
import com.example.myapplication.base.database.dao.LikeDao
import com.example.myapplication.base.database.dao.NewsDao
import com.example.myapplication.base.database.dao.NotifyDao
import com.example.myapplication.base.database.dao.UserDao
import com.example.myapplication.base.database.entity.ChatInfo
import com.example.myapplication.base.database.entity.CommentInfo
import com.example.myapplication.base.database.entity.FeedbackInfo
import com.example.myapplication.base.database.entity.FriendInfo
import com.example.myapplication.base.database.entity.LikeInfo
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.base.database.entity.NotifyInfo
import com.example.myapplication.base.database.entity.User

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