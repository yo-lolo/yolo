package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : NewsInfo
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/26 9:48
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/26 9:48
 * @UpdateRemark : 更新说明
 */
@Entity
data class NewsInfo(
    val number: Long,
    val time: Long,
    val tag: String,
    val type: String,
    val title: String,
    val content: String,
    val image: String = ""
) : java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}