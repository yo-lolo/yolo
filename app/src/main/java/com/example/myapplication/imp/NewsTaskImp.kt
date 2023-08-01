package com.example.myapplication.imp

import com.example.myapplication.api.NewsTask
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.dao.NewsDao
import com.example.myapplication.database.entity.NewsInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : NewsTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:30
 * @UpdateRemark : 更新说明
 */
class NewsTaskImp(
    private val appDataBase: AppDataBase,
    private val newsDao: NewsDao = appDataBase.NewsDao()
) : NewsTask {

    override fun insertNews(newsInfo: NewsInfo): Long {
        return newsDao.insertNews(newsInfo)
    }

    override fun deleteNews(newsInfo: NewsInfo) {
        newsDao.deleteNews(newsInfo)
    }

    override fun getNews(): List<NewsInfo> {
        return newsDao.getNews()
    }

    override fun getNewsById(number: Long): List<NewsInfo> {
        return newsDao.getNewsById(number)
    }
}