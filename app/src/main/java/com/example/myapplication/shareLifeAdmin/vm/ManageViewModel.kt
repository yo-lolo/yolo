package com.example.myapplication.shareLifeAdmin.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseViewModel
import com.example.myapplication.base.database.entity.CommentInfo
import com.example.myapplication.base.database.entity.FeedbackInfo
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.base.database.entity.User
import com.example.myapplication.util.PromptUtils
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
    var comments = MutableLiveData<List<CommentInfo>>()
    var newsList = MutableLiveData<List<NewsInfo>>()

    //是否已经全部勾选
    val isAllCheck = MutableLiveData(false)
    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val feedbackStoreRepository = DataManager.feedbackStoreRepository
    private val commentStoreRepository = DataManager.commentStoreRepository

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
     * 初始化评论
     */
    fun initComments() {
        viewModelScope.launch {
            val data = commentStoreRepository.getAllComment().filter { it.type == 0 }
            comments.value = data
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
                    ToastUtils.showShort("审核成功")
                }.onFailure {
                    ToastUtils.showShort("审核失败")
                }
            }

        }
    }

    /**
     * 全部选择逻辑
     */
    fun onCheckAll() {
        launch {
            val _isAllCheck = isAllCheck.value
            _isAllCheck!!

            comments.value?.forEach {
                it.checked = !_isAllCheck
            }

            //反转是否全选状态
            isAllCheck.value = !_isAllCheck
            //触发监听刷新数据
            comments.value = comments.value
        }
    }


    /**
     * 批量审核评论
     */
    fun onBatchAuditComments() {
        val comments = getCheckedData()
        val commentsId = comments.map { it.id }

        PromptUtils().batchAuditPrompt(commentsId) {
            launchSafe {
                comments.forEach { commentInfo ->
                    commentStoreRepository.updateCommentType(1, commentInfo.id)
                }
                initComments()
            }
        }
    }


    /**
     * 选中状态改变
     */
    fun onCheckChange() {
        launch {
            //计算是否全部选中
            val _isAllCheck = comments.value?.none { !it.checked }
            if (_isAllCheck != isAllCheck.value) {
                isAllCheck.value = _isAllCheck
            }
        }

    }


    fun getCheckedData(): List<CommentInfo> {
        return comments.value?.filter { it.checked } ?: listOf()
    }


}