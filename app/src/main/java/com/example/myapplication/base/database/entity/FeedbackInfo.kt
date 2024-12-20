package com.example.myapplication.base.database.entity

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
 * @CreateDate : 2023/7/18 17:37
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 17:37
 * @UpdateRemark : 更新说明
 */
@Entity
data class FeedbackInfo(
    val phoneNumber: Long,
    val type: String,
    val desc: String,
    val time: Long,
    val pictureItems: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}