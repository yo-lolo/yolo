package com.example.myapplication.bean

import com.example.myapplication.base.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.data
 * @ClassName : NewsDatas
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/25 14:34
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/25 14:34
 * @UpdateRemark : 更新说明
 */
data class NewsDataInfo(
    val user: User,
    val likeCount: Int,
    val likeState: Boolean
) {
}