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
            .insertUser(User(11111111111, "111111", 1659256478548,"付连妹"))
        appDataBase.userDao()
            .insertUser(User(22222222222, "222222", 1689000000000,"汪惠姗"))
        appDataBase.userDao()
            .insertUser(User(33333333333, "333333", 1690700000000,"余露"))
        appDataBase.userDao()
            .insertUser(User(44444444444, "444444", 1691000000000,"方铭姚"))
    }


    suspend fun insertNews() = withContext(Dispatchers.IO) {
        appDataBase.NewsDao()
            .insertNews(NewsInfo(33333333333, 1693216150214, "美食", 0, "我爱大米饭！", "米饭玛喜达","/storage/emulated/0/Pictures/yolo/img-168853985155810d910fa18f90d0e292897f52b664c800496091f38f5a0e57230a1e861732dde.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(44444444444, 1693216494020, "其他", 0, "我是暴躁大王", "我可爱生气了呢","/storage/emulated/0/DCIM/tb_image_share_1688742034263.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(11111111111, 1693216624968, "其他", 0, "我姓付，我幸福！", "我姓付，我幸福","/storage/emulated/0/DCIM/Camera/IMG_20230708_144951.jpg"))
        appDataBase.NewsDao()
            .insertNews(NewsInfo(22222222222, 1693216787020, "出行", 0, "我的小五月", "五月五月，我的好猫咪","/storage/emulated/0/Pictures/weibo/img-16889617830213389a2ae2deeb951e435596fb97f9cb2624c09a6b53818e904ff48d8bc840b3b.jpg"))
    }

}