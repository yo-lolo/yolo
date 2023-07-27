package com.example.myapplication.database.entity

import androidx.room.AutoMigration
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.entity
 * @ClassName : User
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/10 14:32
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/10 14:32
 * @UpdateRemark : 更新说明
 */
@Entity
data class User(
    @PrimaryKey
    var number: Long,
    var pass: String,
    var time: Long,
    var isLogin: Boolean = false,
    var neck: String = "MoMo",
    var image: String = "",
    var date: String = "",
    var sex: String = "",
    var address: String = ""
) {

}