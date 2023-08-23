package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
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
    var user = MutableLiveData<User>()
    var imagePath = MutableLiveData<String>("")


    fun initData() {
        viewModelScope.launch {
            user.value = userStoreRepository.queryUserByNumber(AppConfig.phoneNumber)
        }
    }

    fun refreshPath(path: String) {
        imagePath.value = path
    }

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