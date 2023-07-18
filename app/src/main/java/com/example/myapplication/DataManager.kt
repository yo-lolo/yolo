package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.imp.UserTaskImp
import com.example.myapplication.repos.UserStoreRepository

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication
 * @ClassName : DataManager
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 16:25
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 16:25
 * @UpdateRemark : 更新说明
 */

@SuppressLint("StaticFieldLeak")
object DataManager {

    lateinit var context: Context
    lateinit var userStoreRepository: UserStoreRepository
    private var appDataBase: AppDataBase? = null

    fun init(context: Context) {
        this.context = context
        appDataBase = createDb()
        userStoreRepository = UserStoreRepository(UserTaskImp(appDataBase!!))
    }

    private fun createDb(): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java, "database-my"
        ).build()
    }
}