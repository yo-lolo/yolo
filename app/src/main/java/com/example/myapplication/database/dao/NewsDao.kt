package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.NewsInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.dao
 * @ClassName : NewsDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:09
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:09
 * @UpdateRemark : 更新说明
 */
@Dao
interface NewsDao {
    @Insert
    fun insertNews(newsInfo: NewsInfo): Long

    @Delete
    fun deleteNews(newsInfo: NewsInfo)

    @Query("select * from NewsInfo")
    fun getNews(): List<NewsInfo>

    @Query("select * from NewsInfo where number =:number")
    fun getNewsById(number: Long): List<NewsInfo>

}