package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.NewsDataInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : AppSeachViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:09
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:09
 * @UpdateRemark : 更新说明
 */
class AppSearchViewModel : BaseViewModel() {

    private val newsStoreRepository = DataManager.newsStoreRepository
    private val userStoreRepository = DataManager.userStoreRepository
    private val likeStoreRepository = DataManager.likeStoreRepository
    private val friendsStoreRepository = DataManager.friendsStoreRepository

    /**
     * 查询结果
     */
    val searchNewsResult = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()
    val searchFriendsResult = MutableLiveData<Map<FriendInfo, User>>()


    /**
     * 搜索过滤
     */
    fun doSearch(tag: Int?, searchText: String) {
        if (tag == AppConfig.SEARCH_NEWS) {
            searchNews(searchText)
        }
        if (tag == AppConfig.SEARCH_FRIENDS) {
            searchFriends(searchText)
        }
    }

    /**
     * 搜索文章
     */
    private fun searchNews(searchText: String) {
        launchSafe {
            val resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
            newsStoreRepository.queryNewsBySearchText(searchText).map { newsInfo ->
                val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                val likeState = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                    .any { it.newsId == newsInfo.id }
                resultMap[newsInfo] = NewsDataInfo(user, likeCount, likeState)
            }
            searchNewsResult.value = resultMap.toList()
        }
    }

    /**
     * 搜索好友
     */
    private fun searchFriends(searchText: String) {
        launchSafe {
            // 获取所有已添加的好友
            val mineFriends =
                friendsStoreRepository.getFriendsById(AppConfig.phoneNumber).filter { it.tag == AppConfig.IS_FRIEND }
            val resultMap = mutableMapOf<FriendInfo, User>()
            mineFriends.map {
                val user = userStoreRepository.queryUserByNumber(it.friendNumber)
                resultMap[it] = user
            }
            searchFriendsResult.value = resultMap.toMap().filter {
                it.value.number.toString().contains(searchText) || it.value.neck.toLowerCase()
                    .contains(searchText.toLowerCase())
            }
        }
    }
}