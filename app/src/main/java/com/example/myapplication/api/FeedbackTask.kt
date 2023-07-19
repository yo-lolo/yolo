package com.example.myapplication.api

import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.FeedbackInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : FeedbackTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 17:47
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 17:47
 * @UpdateRemark : 更新说明
 */
interface FeedbackTask {

    /**
     * 添加反馈
     */
    fun insertFeedBack(feedbackInfo: FeedbackInfo): Long

    /**
     * 获取反馈
     */
    fun getAllFeedbacks(): List<FeedbackInfo>

    /**
     * 通过用户number查找反馈
     */
    fun getFeedbackByNumber(number: Long): List<FeedbackInfo>
}