package com.example.myapplication

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/10/20 11:12
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/10/20 11:12
 * @UpdateRemark : 更新说明
 */
object Constants {
    /**
     * app 日志 TAG
     */
    const val BASE_TAG = "YOLO_APP_"

    const val MSG_FROM_CLIENT = 1
    const val MSG_FROM_SERVICE = 1

    /**
     * 是否保存密码
     */
    const val SETTING_IS_SAVE_PASSWORD = "setting_is_save_password"

    /**
     * 是否自动登录
     */
    const val SETTING_IS_AUTO_LOGIN = "setting_is_auto_login"

    /**
     * 保存的用户名
     */
    const val USER_NUM = "user_num"

    /**
     *  保存的用户密码
     */
    const val USER_PASSWORD = "user_password"

    /**
     *  是否展示App简介弹窗
     */
    const val APP_INTRODUCTION_DIALOG = "app_introduction_dialog"

    /**
     * App简介弹窗标题
     */
    const val APP_INTRODUCTION_DIALOG_TITLE = "App简介标题"

    /**
     * App简介弹窗内容
     */
    const val APP_INTRODUCTION_DIALOG_CONTENT = "App简介弹窗内容"

    /**
     * 判断是否登录
     */
    const val IS_LOGIN = "is_login"

    /**
     * 判断是否进行消息提醒
     */
    const val IS_NOTIFY = "is_notify"

    /**
     * 上次写入日志文件名称的 key
     */
    const val LAST_WRITE_X_LOG_FILE_NAME_KEY = "LAST_WRITE_X_LOG_FILE_NAME_KEY"

    /**
     * log路径名
     */
    const val LogFilesDir = "/log"

    /**
     * 崩溃捕捉后的存储位置
     */
    const val CrashFilesDir = "/crash"

}