package com.example.myapplication.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import kotlinx.coroutines.delay

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : LoginViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/11 15:57
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/11 15:57
 * @UpdateRemark : 更新说明
 */
class LoginViewModel : BaseViewModel() {

    private val userStoreRepository = DataManager.userStoreRepository

    var loginType = MutableLiveData(false)
    var adminLoginType = MutableLiveData(false)

    /**
     * 检查用户登录信息
     */
    fun checkLogin(number: String, pass: String) {
        launchSafe {
            if (number.isNotEmpty() && pass.isNotEmpty()) {
                val phoneNumber = number.toLong()
                kotlin.runCatching {
                    userStoreRepository.queryUserByNumber(phoneNumber)
                }.onSuccess {
                    val user = userStoreRepository.queryUserByNumber(phoneNumber)
                    if (user != null) {
                        if (pass.isNotEmpty() && pass == user.pass) {
                            ToastUtils.showShort("登陆成功,进入首页")
                            delay(1000)
                            loginType.value = true

                        } else {
                            loginType.value = false
                            ToastUtils.showShort("密码错误，请重试")
                        }
                    } else {
                        loginType.value = false
                        ToastUtils.showShort("不存在该用户")
                    }
                }.onFailure {
                    Log.e("yolo", it.toString())
                }
            }else{
                ToastUtils.showShort("用户名密码不能为空")
            }
        }
    }

    /**
     * 检查管理员登录信息
     */
    fun checkAdminLogin(number: String, pass: String) {
        launchSafe {
            if (number.isNotEmpty() && pass.isNotEmpty()) {
                if (number.toLong() == 19956596024 && pass == "123456") {
                    ToastUtils.showShort("登陆成功,进入管理员页面")
                    delay(1000)
                    adminLoginType.value = true
                } else {
                    adminLoginType.value = false
                    ToastUtils.showShort("用户名密码错误")
                }
            } else {
                ToastUtils.showShort("用户名密码不能为空")
            }
        }
    }
}