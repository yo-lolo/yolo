package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.imp.FeedbackTaskImp
import com.example.myapplication.imp.UserTaskImp
import com.example.myapplication.repos.FeedbackStoreRepository
import com.example.myapplication.repos.UserStoreRepository
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

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
    lateinit var feedbackStoreRepository: FeedbackStoreRepository
    private var appDataBase: AppDataBase? = null

    fun init(context: Context) {
        this.context = context
        appDataBase = createDb()
        userStoreRepository = UserStoreRepository(UserTaskImp(appDataBase!!))
        feedbackStoreRepository = FeedbackStoreRepository(FeedbackTaskImp(appDataBase!!))
        initSmartRefresh()
    }

    private fun createDb(): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java, "database-my"
        ).build()
    }

    private fun initSmartRefresh() {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout ->
            // 全局设置主题颜色
            layout.setPrimaryColorsId(android.R.color.transparent, R.color.candy_r)
            ClassicsHeader(context)
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    fun layoutManagerNotScroll(): LinearLayoutManager {
        var linearlayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        return linearlayoutManager
    }
}