package com.example.myapplication.config

import android.content.Context
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.isLogin
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

    /**
     * 当前登陆的用户账户
     */
    var phoneNumber: Long = if (isLogin()) MMKVManager.getUserNumAndPass().first.toLong() else 0

    const val IMAGE_OPEN = 0xff
    const val NEWS_IMAGE_OPEN = 1
    const val USER_IMAGE_EDIT = 2

    /**
     * 应用信息
     */
    const val PACKAGE_NAME = "com.example.myapplication"

    const val VERSION_NAME = "1.0.0"

    const val APP_NAME = "YoLo"

    /**
     * 管理员登陆的账户密码（固定）
     */
    const val ADMIN_NUM = 19956596024

    const val ADMIN_PASS = "123456"

    /**
     * 轮播图片集合
     */
    val BANNER_IMAGES = listOf(
        "https://alifei02.cfp.cn/creative/vcg/800/new/VCG21gic19937006.jpg",
        "https://alifei01.cfp.cn/creative/vcg/800/version23/VCG41157532246.jpg",
        "https://tenfei04.cfp.cn/creative/vcg/800/version23/VCG41164857181.jpg",
        "https://alifei05.cfp.cn/creative/vcg/800/version23/VCG41542511145.jpg",
        "https://tenfei03.cfp.cn/creative/vcg/800/new/VCG41N1090223986.jpg"
    )

    /**
     * 搜索标识
     */
    const val SEARCH_NEWS = 0
    const val SEARCH_FRIENDS = 1

    /**
     * 好友标识
     * 0-非好友 1-好友
     */
    const val NOT_FRIEND = 0
    const val IS_FRIEND = 1

}