package com.example.myapplication.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.MainActivity.Companion.TAG
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.getTag
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
                            MMKVManager.isLogin = true
                            AppConfig.phoneNumber = phoneNumber
                            ToastUtils.showShort("登陆成功,进入首页")
                            delay(1000)
                            initMessNotify() // 初始化消息通知
                            loginType.value = true

                        } else {
                            ToastUtils.showShort("密码错误，请重试")
                        }
                    } else {
                        ToastUtils.showShort("不存在该用户")
                    }
                }.onFailure {
                    Log.e(getTag(), it.toString())
                }
            } else {
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
            //TODO 管理员登录测试使用
            adminLoginType.value = true
//            if (number.isNotEmpty() && pass.isNotEmpty()) {
//                if (number.toLong() == AppConfig.ADMIN_NUM && pass == AppConfig.ADMIN_PASS) {
//                    ToastUtils.showShort("登陆成功,进入管理员页面")
//                    delay(1000)
//                    adminLoginType.value = true
//                } else {
//                    ToastUtils.showShort("用户名密码错误")
//                }
//            } else {
//                ToastUtils.showShort("用户名密码不能为空")
//            }
        }
    }

    /**
     * 初始化消息通知
     * 通知信息缓存（审核通过，消息发送成功，好友申请发送成功）
     * Todo: 登录成功后需要初始化消息通知（信息通知，文章审核通知，好友申请通知三种）
     * 信息通知要判断是否开启消息免打扰，若开启则拦截（即删除缓存信息）
     */
    private fun initMessNotify() {

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