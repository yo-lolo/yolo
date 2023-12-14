package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.NewsDataInfo
import com.example.myapplication.database.entity.NewsInfo

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

    /**
     * 查询结果
     */
    val searchResult = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()

    /**
     * 搜索文章
     */
    fun searchNews(searchText: String) {
        launchSafe {
            val resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
            newsStoreRepository.queryNewsBySearchText(searchText).map { newsInfo ->
                val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                val likeState = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                    .any { it.newsId == newsInfo.id }
                resultMap[newsInfo] = NewsDataInfo(user, likeCount, likeState)
            }
            searchResult.value = resultMap.toList()
        }

    }
}