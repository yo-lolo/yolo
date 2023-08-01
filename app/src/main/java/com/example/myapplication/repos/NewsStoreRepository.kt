package com.example.myapplication.repos

import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.imp.NewsTaskImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : NewsStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:49
 * @UpdateRemark : 更新说明
 */
class NewsStoreRepository(private val newsTaskImp: NewsTaskImp) {


    /**
     * 添加新闻
     */
    suspend fun insertNews(newsInfo: NewsInfo): Long = withContext(Dispatchers.IO) {
        return@withContext newsTaskImp.insertNews(newsInfo)
    }

    /**
     * 删除新闻
     */
    suspend fun deleteNews(newsInfo: NewsInfo) = withContext(Dispatchers.IO) {
        return@withContext newsTaskImp.deleteNews(newsInfo)
    }

    /**
     * 获取所有新闻
     */
    suspend fun getNews(): List<NewsInfo> = withContext(Dispatchers.IO) {
        return@withContext newsTaskImp.getNews()
    }

    /**
     * 获取该账号的新闻
     */
    suspend fun getNewsById(number: Long): List<NewsInfo> = withContext(Dispatchers.IO) {
        return@withContext newsTaskImp.getNewsById(number)
    }

}