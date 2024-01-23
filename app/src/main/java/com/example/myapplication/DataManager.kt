package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.broadcastReceiver.MessNotifyReceiver
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.imp.*
import com.example.myapplication.notify.MessNotification
import com.example.myapplication.repos.*
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.coroutines.*

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

    private var appDataBase: AppDataBase? = null

    lateinit var userStoreRepository: UserStoreRepository

    lateinit var feedbackStoreRepository: FeedbackStoreRepository

    lateinit var chatStoreRepository: ChatStoreRepository

    lateinit var friendsStoreRepository: FriendsStoreRepository

    lateinit var newsStoreRepository: NewsStoreRepository

    lateinit var likeStoreRepository: LikeStoreRepository

    lateinit var commentStoreRepository: CommentStoreRepository

    lateinit var testStoreRepository: TestStoreRepository

    val launchScope by lazy {
        val exceptionHandler =
            CoroutineExceptionHandler { _, e -> Log.e(Constants.BASE_TAG, e.toString()) }
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
        scope
    }

    fun init(context: Context) {
        this.context = context

        appDataBase = createDb()

        // 配置用户数据库
        userStoreRepository = UserStoreRepository(UserTaskImp(appDataBase!!))

        feedbackStoreRepository = FeedbackStoreRepository(FeedbackTaskImp(appDataBase!!))

        chatStoreRepository = ChatStoreRepository(ChatTaskImp(appDataBase!!))

        friendsStoreRepository = FriendsStoreRepository(FriendTaskImp(appDataBase!!))

        newsStoreRepository = NewsStoreRepository(NewsTaskImp(appDataBase!!))

        likeStoreRepository = LikeStoreRepository(LikeTaskImp(appDataBase!!))

        testStoreRepository = TestStoreRepository(appDataBase!!)

        commentStoreRepository = CommentStoreRepository(CommentTaskImp(appDataBase!!))

        initSmartRefresh()

        initNotifyListener()

        MMKVManager

        launchScope.launch {
            if (userStoreRepository.getUsers().isEmpty()){
                initData()
            }
        }

    }

    /**
     * 构建数据库
     */
    private fun createDb(): AppDataBase {
        return Room.databaseBuilder(
            context, AppDataBase::class.java, "database-my"
        ).build()
    }

    /**
     * 初始化刷新
     */
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

    /**
     * 设置RecyclerView在垂直状态下不滑动
     */
    fun layoutManagerNotScroll(): LinearLayoutManager {
        val linearlayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        return linearlayoutManager
    }

    fun GridLayoutManagerNotScroll(spanCount: Int): GridLayoutManager {
        val gridLayoutManager: GridLayoutManager = object : GridLayoutManager(context, spanCount) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        return gridLayoutManager
    }

    /**
     * 注册消息推送广播
     */
    private fun initNotifyListener() {
        MessNotifyReceiver.registerMessNotifyReceiver(context)
    }

    private fun initData() {
        launchScope.launch {
            testStoreRepository.insertUser()
            testStoreRepository.insertNews()
        }
    }

}