package com.example.myapplication.base.cache

import com.example.myapplication.common.Constants
import com.example.myapplication.getTag
import com.example.myapplication.base.log.SpeedyLog
import com.tencent.mmkv.MMKV
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.data
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/10/20 13:36
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/10/20 13:36
 * @UpdateRemark : 更新说明
 */
object MMKVManager {

    private val mmkv: MMKV by lazy {
        val mmkv = MMKV.mmkvWithID("MMKVManager")
        mmkv
    }

    /**
     * 是否保存密码
     */
    var isSavePass: Boolean
        get() {
            return mmkv.getBoolean(Constants.SETTING_IS_SAVE_PASSWORD, false)
        }
        set(value) {
            SpeedyLog.d(getTag(), "是否保存密码 : $value")
            mmkv.putBoolean(Constants.SETTING_IS_SAVE_PASSWORD, value)
        }

    /**
     * 是否自动登录
     */
    var isAutoLogin: Boolean
        get() {
            return mmkv.getBoolean(Constants.SETTING_IS_AUTO_LOGIN, false)
        }
        set(value) {
            SpeedyLog.d(getTag(), "是否自动登录 : $value")
            mmkv.putBoolean(Constants.SETTING_IS_AUTO_LOGIN, value)
        }

    /**
     *  是否展示App简介弹窗
     */
    var isShowAppIntroductionDialog: Boolean
        get() {
            return mmkv.getBoolean(Constants.APP_INTRODUCTION_DIALOG, true)
        }
        set(value) {
            SpeedyLog.d(getTag(), "是否展示App简介弹窗 : $value")
            mmkv.putBoolean(Constants.APP_INTRODUCTION_DIALOG, value)
        }

    /**
     * 判断是否登录
     */
    var isLogin: Boolean
        get() {
            return mmkv.getBoolean(Constants.IS_LOGIN, false)
        }
        set(value) {
            SpeedyLog.d(getTag(), "是否登录 : $value")
            mmkv.putBoolean(Constants.IS_LOGIN, value)
        }

    /**
     * 判断是否需要消息提醒(默认开启：true)
     */
    var isNotify: Boolean
        get() {
            return mmkv.getBoolean(Constants.IS_NOTIFY, true)
        }
        set(value) {
            SpeedyLog.d(getTag(), "是否需要消息提醒 : $value")
            mmkv.putBoolean(Constants.IS_NOTIFY, value)
        }

    /**
     * 日志文件名称
     */
    var LogFileName: String
        get() {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val nowDate = format.format(Date())
            val defaultLogFileName = "${nowDate}_log.txt"
            return mmkv.getString(Constants.LAST_WRITE_X_LOG_FILE_NAME_KEY, defaultLogFileName)
                ?: defaultLogFileName
        }
        set(value) {
            SpeedyLog.d(getTag(), "设置日志文件名称 : $value")
            mmkv.getString(Constants.LAST_WRITE_X_LOG_FILE_NAME_KEY, value)
        }


    /**
     * 保存用户的用户名、密码
     */
    fun saveUserNumAndPass(number: String, pass: String) {
        mmkv.putString(Constants.USER_NUM, number)
        mmkv.putString(Constants.USER_PASSWORD, pass)
    }

    /**
     * 获取用户的用户名、密码
     */
    fun getUserNumAndPass(): Pair<String, String> {
        val num = mmkv.getString(Constants.USER_NUM, "0") ?: "0"
        val pass = mmkv.getString(Constants.USER_PASSWORD, "") ?: ""
        return num to pass
    }

}