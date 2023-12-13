package com.example.myapplication.data

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.data
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/12/13 15:55
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/12/13 15:55
 * @UpdateRemark : 更新说明
 */
data class NotifyData(
    val title: String,
    val content: String,
    val tag: Int? = null,
    val data: Any? = null
) : java.io.Serializable {
}