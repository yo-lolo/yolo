package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : ApkInfo
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:17
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:17
 * @UpdateRemark : 更新说明
 */
@Entity
data class ApkInfo(
    var type: Int,
    var clientId: String,
    var imei: String,
    var desc: String,
    var time: String,
    var action: String = "1"
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}