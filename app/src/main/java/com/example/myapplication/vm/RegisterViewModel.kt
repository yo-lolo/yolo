package com.example.myapplication.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.entity.User
import com.example.myapplication.util.TimeUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : RegisterViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 15:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 15:49
 * @UpdateRemark : 更新说明
 */
class RegisterViewModel : BaseViewModel() {

    private val userStoreRepository = DataManager.userStoreRepository

    var registerType = MutableLiveData(false)

    fun register(number: String, pass: String, pass2: String) {
        launchSafe {
            if (number.isNotEmpty() && pass.isNotEmpty() && pass.isNotEmpty()) {
                if (number.length == 11) {
                    var phoneNumber = number.toLong()
                    if (pass == pass2 && pass.length in 6..14) {
                        kotlin.runCatching {
                            userStoreRepository.insertUser(User(phoneNumber, pass, TimeUtil.getCurrentMill()))
                        }.onFailure {
                            registerType.value = false
                            ToastUtils.showShort("注册失败，请重试")
                            Log.e("yolo", it.toString())
                        }.onSuccess {
                            ToastUtils.showShort("注册成功，跳转到登陆页面")
                            delay(1000)
                            registerType.value = true
                        }
                    } else {
                        ToastUtils.showShort("密码不一致或格式不正确")
                    }
                } else {
                    ToastUtils.showShort("手机号格式不正确")
                }
            } else {
                ToastUtils.showShort("用户名密码不能为空")
            }
        }
    }
}