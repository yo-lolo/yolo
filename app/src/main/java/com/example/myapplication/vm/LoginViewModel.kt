package com.example.myapplication.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.MainActivity.Companion.TAG
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.data.MMKVManager
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
    val isAutoLoginLiveData = MutableLiveData(MMKVManager.isAutoLogin)

    val isSaveUserPasswordLiveData = MutableLiveData(MMKVManager.isSavePass)

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
                            saveUserNumAndPass(number, pass)
                            ToastUtils.showShort("登陆成功,进入首页")
                            delay(1000)
                            loginType.value = true

                        } else {
                            ToastUtils.showShort("密码错误，请重试")
                        }
                    } else {
                        ToastUtils.showShort("不存在该用户")
                    }
                }.onFailure {
                    Log.e("yolo", it.toString())
                }
            } else {
                //TODO 测试使用
                loginType.value = true
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
                    ToastUtils.showShort("用户名密码错误")
                }
            } else {
                //TODO 测试使用
                adminLoginType.value = true
                ToastUtils.showShort("用户名密码不能为空")
            }
        }
    }

    /**
     * 保存用户名和密码
     */
    fun saveUserNumAndPass(number: String, password: String) {
        Log.e(TAG, "saveUserNameAndPassword >>> 保存用户名和密码 用户名: $number, 密码: $password")
        MMKVManager.saveUserNumAndPass(number, password)
    }

    /**
     * 获取保存的用户名和密码
     */
    fun getUserNumAndPass() = MMKVManager.getUserNumAndPass()

    /**
     * 获取当前自动登陆状态
     */
    fun getAutoLoginType() = MMKVManager.isAutoLogin

    /**
     * 自动登录
     */
    fun doAutoLogin() {
        val (num, pass) = getUserNumAndPass()
        checkLogin(num, pass)
    }


}