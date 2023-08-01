package com.example.myapplication.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.NewsInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : NewsTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:28
 * @UpdateRemark : 更新说明
 */
interface NewsTask {

    /**
     * 添加新闻
     */
    fun insertNews(newsInfo: NewsInfo): Long

    /**
     * 删除新闻
     */
    fun deleteNews(newsInfo: NewsInfo)

    /**
     * 获取所有新闻
     */
    fun getNews(): List<NewsInfo>

    /**
     * 获取该账号的新闻
     */
    fun getNewsById(number: Long): List<NewsInfo>
}