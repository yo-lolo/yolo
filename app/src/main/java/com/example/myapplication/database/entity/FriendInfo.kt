package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.common.AppConfig

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : FeedbackInfo
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 09:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 09:28
 * @UpdateRemark : 更新说明
 */
@Entity
data class FriendInfo(
    val number: Long,
    val friendNumber: Long,
    val time: Long,
    val image: String,
    val tag: Int = AppConfig.NOT_FRIEND,
    val isTop: Boolean = false,
    val isNotify: Boolean = true
) : java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}