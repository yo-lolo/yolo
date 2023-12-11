package com.example.myapplication.admin.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.entity.FeedbackInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.adimin.vm
 * @ClassName : UserViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/25 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/25 16:30
 * @UpdateRemark : 更新说明
 */
class ManageViewModel : BaseViewModel() {

    var userList = MutableLiveData<List<User>>()
    var feedbacks = MutableLiveData<List<FeedbackInfo>>()
    var newsList = MutableLiveData<List<NewsInfo>>()
    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val feedbackStoreRepository = DataManager.feedbackStoreRepository

    /**
     * 初始化用户数据
     */
    fun initUsersData() {
        viewModelScope.launch {
            userList.value = userStoreRepository.getUsers()
        }
    }

    /**
     * 初始化文章数据
     */
    fun initNews() {
        viewModelScope.launch {
            newsList.value = newsStoreRepository.getNews()
        }
    }

    /**
     * 初始化反馈数据
     */
    fun initFeedbacks() {
        viewModelScope.launch {
            feedbacks.value = feedbackStoreRepository.getAllFeedbacks()
        }
    }

    /**
     * 文章审核
     */
    fun updateNewsAuditType(type: Int, newsInfo: NewsInfo) {
        viewModelScope.launch {
            launchSafe {
                kotlin.runCatching {
                    newsStoreRepository.updateNewsAuditType(type, newsInfo.id)
                }.onSuccess {
                    initNews()
                }.onFailure {
                    ToastUtils.showShort("审核失败")
                }
            }

        }
    }


}