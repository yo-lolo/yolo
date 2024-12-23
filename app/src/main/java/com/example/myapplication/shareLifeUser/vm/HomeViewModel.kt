package com.example.myapplication.shareLifeUser.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseViewModel
import com.example.myapplication.common.AppConfig
import com.example.myapplication.bean.NewsDataInfo
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.getTag
import com.example.myapplication.base.log.SpeedyLog
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

    var newsPostState = MutableLiveData<Boolean>(false)

    fun initData() {
        launchSafe {
            val resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
            newsStoreRepository.getNews().map { newsInfo ->
                val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                val likeState = likeStoreRepository.getLikesMine(AppConfig.account)
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

    /**
     * 发布文章
     */
    fun onSubmit(title: String, content: String) {
        SpeedyLog.d(getTag(),"onSubmit >>> 发布文章")
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
                    SpeedyLog.d(getTag(),"<<< 发布成功，待管理员审核")
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