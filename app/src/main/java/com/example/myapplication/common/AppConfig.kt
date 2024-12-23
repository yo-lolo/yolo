package com.example.myapplication.common

import com.example.myapplication.base.cache.MMKVManager
import com.example.myapplication.isLogin

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
    var account: Long = if (isLogin()) MMKVManager.getUserNumAndPass().first.toLong() else 0

    const val IMAGE_OPEN = 0xff
    const val NEWS_IMAGE_OPEN = 1
    const val USER_IMAGE_EDIT = 2

    /**
     * 管理员登陆的账户密码（固定）
     */
    const val ADMIN_NUM = 19956596024

    const val ADMIN_PASS = "123456"

    /**
     * 轮播图片集合
     */
    val BANNER_IMAGES = listOf(
        "https://cdn.pixabay.com/photo/2024/07/18/22/39/ai-generated-8905163_1280.jpg",
        "https://cdn.pixabay.com/photo/2024/07/16/08/15/ai-generated-8898676_1280.png",
        "https://cdn.pixabay.com/photo/2024/07/17/00/26/ai-generated-8900260_1280.jpg",
        "https://cdn.pixabay.com/photo/2024/07/18/22/39/ai-generated-8905162_1280.jpg",
        "https://cdn.pixabay.com/photo/2024/07/13/20/02/cheeseburger-8893011_1280.png"
    )

    /**
     * 搜索标识
     */
    const val SEARCH_NEWS = 0
    const val SEARCH_FRIENDS = 1
    const val SEARCH_CHATS = 2

    /**
     * 好友标识
     * 0-非好友 1-好友
     * @sample NOT_FRIEND 非好友标识
     * @sample IS_FRIEND 好友标识
     */
    const val NOT_FRIEND = 0
    const val IS_FRIEND = 1

    /**
     * 通知标识
     * 信息通知，文章审核通知，好友申请通知
     */
    const val MESS_NOTIFY = 0
    const val AUDIT_NOTIFY = 1
    const val FRIEND_APPLY_NOTIFY = 2

}