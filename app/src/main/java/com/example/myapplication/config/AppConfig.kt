package com.example.myapplication.config

import android.content.Context
import java.io.File
import java.util.IdentityHashMap

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


    //const val phoneNumber: Long = 44444444444
    //const val phoneNumber: Long = 22222222222
    const val BASE_TAG = "yolo"
    //const val phoneNumber: Long = 11111111111
    const val phoneNumber: Long = 44444444444
    const val IMAGE_OPEN = 0xff
    const val NEWS_IMAGE_OPEN = 1
    const val USER_IMAGE_EDIT = 2

    //枚举反馈类型, 1、质量问题，2、使用问题 3、其他
    enum class FeedbackType(typeId: String, desc: String) {
        QA_TYPE("1", "质量问题"),
        USE_TYPE("2", "使用问题"),
        OTHER_TYPE("3", "其他"),
    }
}