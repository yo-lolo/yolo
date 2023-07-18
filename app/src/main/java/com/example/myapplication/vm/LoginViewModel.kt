package com.example.myapplication.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel

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

    /**
     * 检查用户登录信息
     */
    fun checkLogin(number: Int, pass: String) {
        launchSafe {
            kotlin.runCatching {
                userStoreRepository.queryUserByNumber(number)
            }.onSuccess {
                val user = userStoreRepository.queryUserByNumber(number)
                if (user != null) {
                    if (pass.isNotEmpty() && pass == user.pass) {
                        loginType.value = true
                        ToastUtils.showShort("登陆成功")
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
        }
    }

    /**
     * 检查管理员登录信息
     */
    fun checkAdminLogin(number: Int, pass: String): Boolean {
        return (15755949344).toInt() == number && pass == "123456"
    }
}