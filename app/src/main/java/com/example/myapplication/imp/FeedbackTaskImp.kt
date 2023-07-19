package com.example.myapplication.imp

import com.example.myapplication.api.FeedbackTask
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.dao.FeedbackDao
import com.example.myapplication.database.entity.FeedbackInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : FeedbackTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 17:48
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 17:48
 * @UpdateRemark : 更新说明
 */
class FeedbackTaskImp(
    appDataBase: AppDataBase,
    private val feedbackDao: FeedbackDao = appDataBase.feedbackDao()
) : FeedbackTask {
    override fun insertFeedBack(feedbackInfo: FeedbackInfo): Long {
        return feedbackDao.insertFeedBack(feedbackInfo)
    }

    override fun getAllFeedbacks(): List<FeedbackInfo> {
        return feedbackDao.getAllFeedbacks()
    }

    override fun getFeedbackByNumber(number: Long): List<FeedbackInfo> {
        return feedbackDao.getFeedbackByNumber(number)
    }
}