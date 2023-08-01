package com.example.myapplication.repos

import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.entity.*
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
            .insertUser(User(11111111111, "111111", 1659256478548))
        appDataBase.userDao()
            .insertUser(User(22222222222, "222222", 1689000000000))
        appDataBase.userDao()
            .insertUser(User(33333333333, "333333", 1690700000000))
        appDataBase.userDao()
            .insertUser(User(44444444444, "444444", 1691000000000))
    }


    suspend fun insertNews() = withContext(Dispatchers.IO) {
        appDataBase.NewsDao()
            .insertNews(NewsInfo(33333333333, 1, "美食", "2", "世界上最美味的旋转小火锅！", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(44444444444, 1, "汽车", "2", "广汽集团", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(11111111111, 1690789826637, "支付宝", "2", "支付宝额度提升啦！", "没想好"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(22222222222, 1690789826637, "微信", "2", "微信闪退怎么回事？", "没想好"))
    }

}