package com.example.myapplication.bean

import com.example.myapplication.base.database.entity.ChatInfo
import com.example.myapplication.base.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.data
 * @ClassName : MessData
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/28 15:22
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/28 15:22
 * @UpdateRemark : 更新说明
 */
class MessData(
    var newChatInfo: ChatInfo,
    var friendUser: User
) {
}