package com.example.myapplication.base.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : FeedbackInfo
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 09:29
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 09:30
 * @UpdateRemark : 更新说明
 */
@Entity
data class ChatInfo(
    val sender: Long,
    val receiver: Long,
    val sendTag: Long,
    val time: Long,
    val content: String,
    val isRead: Boolean = false
) :java.io.Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}