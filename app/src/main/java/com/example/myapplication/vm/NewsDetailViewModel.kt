package com.example.myapplication.vm

import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : NewsDetailViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 11:13
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 11:13
 * @UpdateRemark : 更新说明
 */
class NewsDetailViewModel : BaseViewModel() {

    private val testStoreRepository = DataManager.testStoreRepository

    fun insertFriend(friendNumber: Long) {
        launchSafe {
            testStoreRepository.insertFriend(friendNumber)
        }
    }
}