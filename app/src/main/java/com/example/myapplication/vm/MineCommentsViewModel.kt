package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.MineComments
import com.example.myapplication.data.NewsDataInfo
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
    private val likeStoreRepository = DataManager.likeStoreRepository
    var user = MutableLiveData<User>()
    val news = MutableLiveData<List<NewsInfo>>()
    val commentsNewsMap = MutableLiveData<Map<CommentInfo, MineComments>>()
    val likesNewsMap = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()


    /**
     * 初始化我的评论展示
     */
    fun initComments(number: Long = AppConfig.phoneNumber) {
        viewModelScope.launch {
            val resultMap = mutableMapOf<CommentInfo, MineComments>()
            user.value = userStoreRepository.queryUserByNumber(number)
            news.value = newsStoreRepository.getNewsByNumber(number)
            commentStoreRepository.getCommentsByNumber(number).map {
                var user: User? = null
                var newsUser: User? = null
                val news = newsStoreRepository.getNewsById(it.newsId)
                if (news != null) {
                    user = userStoreRepository.queryUserByNumber(it.number)
                    newsUser = userStoreRepository.queryUserByNumber(news.number)
                }
                if (it.replyId != null) {
                    var user2: User? = null
                    val reply = commentStoreRepository.getCommentsById(it.replyId)
                    if (it.replyNumber != null) {
                        user2 = userStoreRepository.queryUserByNumber(it.replyNumber)
                    }
                    resultMap[it] = MineComments(newsUser, news, user, reply, user2)
                } else {
                    resultMap[it] = MineComments(newsUser, news, user)
                }
            }

            val mapSortByTime = resultMap.toList().sortedByDescending { it.first.time.toLong() }
            commentsNewsMap.value = mapSortByTime.toMap()
        }
    }

    /**
     * 初始化我的点赞
     */
    fun initLikes() {

        viewModelScope.launch {
            val resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
            val newsIdsByLike =
                likeStoreRepository.getLikesMine(AppConfig.phoneNumber).map { it.newsId }
            val newsList = newsIdsByLike.map { newsId ->
                newsStoreRepository.getNewsById(newsId)
            }
            newsList.map { newsInfo ->
                val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                val likeState = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                    .any { it.newsId == newsInfo.id }
                resultMap[newsInfo] = NewsDataInfo(user, likeCount, likeState)
            }
            likesNewsMap.value = resultMap.toList()
        }
    }


}