package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.FeedbackInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.Dao
 * @ClassName : FeedbackDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 17:39
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 17:39
 * @UpdateRemark : 更新说明
 */
@Dao
interface FeedbackDao {
    @Insert
    fun insertFeedBack(feedbackInfo: FeedbackInfo): Long

    @Query("select * from FeedbackInfo")
    fun getAllFeedbacks(): List<FeedbackInfo>

    @Query("select * from FeedbackInfo where phoneNumber = :number ")
    fun getFeedbackByNumber(number: Long): List<FeedbackInfo>
}