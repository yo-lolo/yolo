package com.example.myapplication.config

import android.content.Context
import java.io.File

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.config
 * @ClassName : AppConfig
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 14:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 14:04
 * @UpdateRemark : 更新说明
 */
object AppConfig {


    val AUTO_UPDATE_METHOD_LIST = arrayOf("仅WLAN下", "仅移动网络下", "所有网络下")
    val AUTO_UPDATE_METHOD_LIST_CODE = arrayOf("1", "2", "3")
    val APP_REMIND_METHOD_LIST = arrayOf("每次都提醒", "仅一次", "从不")
    val APP_REMIND_METHOD_LIST_CODE = arrayOf("1", "2", "3")
    val IMAGE_OPEN = 0xff

    //反馈类型, 1、应用市场问题反馈，2、APP问题反馈
    val FEED_BACK_TYPE_SYS = 1
    val FEED_BACK_TYPE_APP = 2
}