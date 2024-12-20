package com.example.myapplication.core.repos

import com.example.myapplication.core.api.FeedbackTask
import com.example.myapplication.base.database.entity.FeedbackInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : FeedbackStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 17:50
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 17:50
 * @UpdateRemark : 更新说明
 */
class FeedbackStoreRepository(private val feedbackTask: FeedbackTask) {

    /**
     * 添加反馈
     */
    suspend fun insertFeedBack(feedbackInfo: FeedbackInfo): Long = withContext(Dispatchers.IO) {
        return@withContext feedbackTask.insertFeedBack(feedbackInfo)
    }

    /**
     * 获取反馈
     */
    suspend fun getAllFeedbacks(): List<FeedbackInfo> = withContext(Dispatchers.IO) {
        return@withContext feedbackTask.getAllFeedbacks()
    }

    /**
     * 通过用户id查找反馈
     */
    suspend fun getFeedbackByNumber(number: Long): List<FeedbackInfo> = withContext(Dispatchers.IO) {
        return@withContext feedbackTask.getFeedbackByNumber(number)
    }
}