package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.AppDataBase

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication
 * @ClassName : MainApplication
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 11:31
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 11:31
 * @UpdateRemark : 更新说明
 */
class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    fun init(context: Context){
        createDb()
    }

    private fun createDb(): AppDataBase {
        return Room.databaseBuilder(
            this,
            AppDataBase::class.java, "database-my"
        ).build()
    }
}