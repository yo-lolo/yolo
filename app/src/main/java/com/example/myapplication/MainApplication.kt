package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.imp.UserTaskImp
import com.example.myapplication.repos.UserStoreRepository
import com.tencent.mmkv.MMKV

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

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)
        MMKV.initialize(this)
    }

    fun init(context: Context) {
        DataManager.init(context)
    }

}

fun Any.getTag() = Constants.BASE_TAG + this.javaClass.simpleName

fun Any.isLogin() = MMKVManager.isLogin

fun Any.isNotify() = MMKVManager.isNotify