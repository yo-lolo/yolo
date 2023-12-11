package com.example.myapplication.vm

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.NewsDataInfo
import com.example.myapplication.database.entity.NewsInfo
import kotlinx.coroutines.delay

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
    private val userStoreRepository = DataManager.userStoreRepository
    private val likeStoreRepository = DataManager.likeStoreRepository
    var newsMapData = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()
    var imagePath = MutableLiveData<String>("")
    var tagText = MutableLiveData<String>("")
    var resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
    var newsPostState = MutableLiveData<Boolean>(false)

    fun initData() {
        launchSafe(false) {
            newsStoreRepository.getNews().map { newsInfo ->
                val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                val likeState = likeStoreRepository.getLikesMine(AppConfig.phoneNumber)
                    .any { it.newsId == newsInfo.id }
                resultMap[newsInfo] = NewsDataInfo(user, likeCount, likeState)
            }
            newsMapData.value = resultMap.toList()
        }
    }

    fun refreshPath(path: String) {
        imagePath.value = path
    }

    fun refreshTag(tag: String) {
        tagText.value = tag
    }

    fun onSubmit(title: String, content: String) {
        if (title.isNotEmpty() && content.isNotEmpty() && tagText.value!!.isNotEmpty()) {
            launchSafe {
                kotlin.runCatching {
                    newsStoreRepository.insertNews(
                        title,
                        content,
                        imagePath.value!!,
                        tagText.value!!
                    )
                }.onSuccess {
                    ToastUtils.showShort("发布成功，待管理员审核")
                    delay(1000)
                    newsPostState.value = true
                }
            }
        } else {
            ToastUtils.showShort("请完成文章填写")
        }
    }
}