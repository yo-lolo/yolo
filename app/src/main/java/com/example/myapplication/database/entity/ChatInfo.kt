package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.JsonParseException

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
    val number: Long,
    val friendNumber: Long,
    val sendTag: Long,
    val time: Long,
    val content: String,
    val is_read: Boolean = false
) :java.io.Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}