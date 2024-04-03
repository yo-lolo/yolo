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
            .insertUser(User(11111111111, "111111", 1659256478548,"FLM"))
        appDataBase.userDao()
            .insertUser(User(22222222222, "222222", 1689000000000,"WHS"))
        appDataBase.userDao()
            .insertUser(User(33333333333, "333333", 1690700000000,"YL"))
        appDataBase.userDao()
            .insertUser(User(44444444444, "444444", 1691000000000,"FMY"))
    }


    suspend fun insertNews() = withContext(Dispatchers.IO) {
        appDataBase.NewsDao()
            .insertNews(NewsInfo(33333333333, 1693216150214, "美食", 0, "痞老板的小宝贝", "痞老板的小宝贝","/storage/emulated/0/Pictures/weibo/img-1710208715803b58540dc5982971d485aa2e292cda0438257c5dff7753dc9a2780be1247da2b7.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(44444444444, 1693216494020, "其他", 0, "冒菜我的爱", "大冤种(梦想成为蟹老板版)","/storage/emulated/0/Pictures/WeiXin/mmexport1709193038438.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(11111111111, 1693216624968, "其他", 0, "我姓付，我幸福！", "章鱼哥的迷妹","/storage/emulated/0/DCIM/Screenshots/Screenshot_2024-02-29-16-03-55-280_com.taobao.idlefish.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(22222222222, 1693216787020, "出行", 0, "我的小五月", "五月五月，我的好猫咪","/storage/emulated/0/Pictures/WeiXin/mmexport1709695731798.jpg"))
    }

}