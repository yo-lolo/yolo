package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.MineComments
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : MineViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/24 16:51
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/24 16:51
 * @UpdateRemark : 更新说明
 */
class MineCommentsViewModel : BaseViewModel() {

    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val commentStoreRepository = DataManager.commentStoreRepository
    var user = MutableLiveData<User>()
    val news = MutableLiveData<List<NewsInfo>>()
    val commentsNewsMap = MutableLiveData<Map<CommentInfo, MineComments>>()
    var resultMap = mutableMapOf<CommentInfo, MineComments>()


    fun initComments(number: Long = AppConfig.phoneNumber) {
        viewModelScope.launch {
            user.value = userStoreRepository.queryUserByNumber(number)
            news.value = newsStoreRepository.getNewsByNumber(number)

            commentStoreRepository.getCommentsByNumber(number).map {
                var user: User? = null
                val news = newsStoreRepository.getNewsById(it.newsId)
                if (news != null) {
                    user = userStoreRepository.queryUserByNumber(it.number)
                }
                if (it.replyId != null) {
                    var user2: User? = null
                    val reply = commentStoreRepository.getCommentsById(it.replyId)
                    if (it.replyNumber != null) {
                        user2 = userStoreRepository.queryUserByNumber(it.replyNumber)
                    }
                    resultMap[it] = MineComments(news, user, reply, user2)
                } else {
                    resultMap[it] = MineComments(news, user)
                }
            }

            val mapSortByTime = resultMap.toList().sortedByDescending { it.first.time.toLong() }
            commentsNewsMap.value = mapSortByTime.toMap()
        }
    }


}