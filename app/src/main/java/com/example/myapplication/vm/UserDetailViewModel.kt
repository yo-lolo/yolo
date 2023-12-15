package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.User
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : UserDetailViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 15:46
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 15:46
 * @UpdateRemark : 更新说明
 */
class UserDetailViewModel : BaseViewModel() {
    var userDetailInfo = MutableLiveData<User>()
    var isFriend = MutableLiveData<Boolean>(false)
    private val userStoreRepository = DataManager.userStoreRepository
    private val friendsStoreRepository = DataManager.friendsStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository

    fun initUserInfo(number: Long) {
        viewModelScope.launch {
            userDetailInfo.value = userStoreRepository.queryUserByNumber(number)
            val friendInfo = friendsStoreRepository.getFriendById(number)
            if (friendInfo != null) {
                isFriend.value = friendInfo.tag == AppConfig.IS_FRIEND
            }
            if (number == AppConfig.phoneNumber) {
                isFriend.value = true
            }
        }
    }

    /**
     * 添加好友
     */
    fun insertFriend(newsId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                friendsStoreRepository.insertFriend(newsId)
            }.onSuccess {
                ToastUtils.showLong("已发送好友请求,等待好友验证")
            }.onFailure {
                ToastUtils.showLong("好友请求失败，请稍后重试")
            }
            initUserInfo(newsId)
        }
    }
}