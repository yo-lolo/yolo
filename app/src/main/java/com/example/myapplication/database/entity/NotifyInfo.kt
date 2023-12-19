package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.config.AppConfig
/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.database.entity
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/12/19 10:42
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/12/19 10:42
 * @UpdateRemark : 更新说明
 */
@Entity
data class NotifyInfo(
    var tag: Int,
    var content: String,
    var jsonData: String
) : java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var title: String = when (tag) {
        AppConfig.MESS_NOTIFY -> "消息通知"
        AppConfig.AUDIT_NOTIFY -> "文章审核通知"
        AppConfig.FRIEND_APPLY_NOTIFY -> "好友申请通知"
        else -> ""
    }
}