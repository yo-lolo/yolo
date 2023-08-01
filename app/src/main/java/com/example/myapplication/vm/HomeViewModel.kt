package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.entity.NewsInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : HomeViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:51
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:51
 * @UpdateRemark : 更新说明
 */
class HomeViewModel : BaseViewModel() {

    private val newsStoreRepository = DataManager.newsStoreRepository
    var news = MutableLiveData<List<NewsInfo>>()

    fun initData() {
        launchSafe {
            news.value = newsStoreRepository.getNews()
        }
    }
}