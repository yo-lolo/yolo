package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
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
 * @CreateDate : 2023/7/27 10:21
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 10:21
 * @UpdateRemark : 更新说明
 */
class MineViewModel : BaseViewModel() {

    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val likeStoreRepository = DataManager.likeStoreRepository
    var user = MutableLiveData<User?>()
    var newsMapData = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()
    var resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
    var imagePath = MutableLiveData<String>("")

    /**
     * 初始化用户数据，默认为当前用户
     */
    fun initData(number: Long = AppConfig.phoneNumber) {
        viewModelScope.launch {
            user.value = userStoreRepository.queryUserByNumber(number)
            newsStoreRepository.getNews().filter { it.type == 1 && it.number == number }.map { newsInfo ->
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

    /**
     * 更新用户信息
     */
    fun updateUserEdit(neck: String, address: String, sign: String) {
        if (user.value != null) {
            viewModelScope.launch {
                kotlin.runCatching {
                    userStoreRepository.updateUserInfo(
                        AppConfig.phoneNumber, neck, address, sign, imagePath.value!!
                    )
                }.onFailure {
                    ToastUtils.showShort("保存失败")
                }.onSuccess {
                    ToastUtils.showShort("保存成功")
                }
            }
        } else {
            ToastUtils.showShort("请先登录")
        }
    }


}