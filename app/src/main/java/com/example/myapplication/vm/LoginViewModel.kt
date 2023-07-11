package com.example.myapplication.vm

import android.widget.Toast
import androidx.lifecycle.ViewModel

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
class LoginViewModel : ViewModel() {


    /**
     * 检查用户登录信息
     */
    fun checkLogin(number: Int, pass: String) {

    }

    /**
     * 检查管理员登录信息
     */
    fun checkAdminLogin(number: Int, pass: String): Boolean {
        return (15755949344).toInt() == number && pass == "123456"
    }
}