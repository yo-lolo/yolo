package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : CommentInfo
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/26 10:03
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/26 10:03
 * @UpdateRemark : 更新说明
 */

@Entity
data class LikeInfo(
    val number: Long,
    val newsId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}