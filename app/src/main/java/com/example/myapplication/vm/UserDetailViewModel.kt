package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
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
    private val userStoreRepository = DataManager.userStoreRepository

    fun initUserInfo(number: Long) {
        viewModelScope.launch {
            userDetailInfo.value = userStoreRepository.queryUserByNumber(number)
        }
    }
}