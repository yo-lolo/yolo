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
data class CommentInfo(
    val newsId: Long,
    val number: Long,
    val content: String,
    val time: Long,
    var level: Int = 1,
    var checked: Boolean = false, //全选字段
    val replyNumber: Long? = null,
    val replyId: Long? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var type : Int = 0

    //replyId 为回复的id 默认为空 当level为2时需要传值
    //level有两级 为1时回复的是当前newsId的作者 为2 回复的是评论里面的用户
}