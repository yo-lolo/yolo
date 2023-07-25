package com.example.myapplication.admin.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.entity.User
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.adimin.vm
 * @ClassName : UserViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/25 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/25 16:30
 * @UpdateRemark : 更新说明
 */
class UserViewModel : BaseViewModel() {

    var userList = MutableLiveData<List<User>>()

    fun initData() {
        viewModelScope.launch {
            userList.value = DataManager.userStoreRepository.getUsers()
        }
    }


}