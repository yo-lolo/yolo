package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.Dao.ApkInfoDao
import com.example.myapplication.database.Dao.UserDao
import com.example.myapplication.database.entity.ApkInfo
import com.example.myapplication.database.entity.User

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
    entities = [ApkInfo::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun apkInfoDao(): ApkInfoDao
    abstract fun userDao(): UserDao
}