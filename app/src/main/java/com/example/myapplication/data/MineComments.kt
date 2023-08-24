package com.example.myapplication.data

import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.data
 * @ClassName : MineComments
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/24 15:07
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/24 15:07
 * @UpdateRemark : 更新说明
 */

/**
 * comment : 主评论
 * replyComment : 副评论 - 主评论回复的评论，主评论level为1时为空
 * newsInfo : 主评论对应的新闻
 * user : 主评论对应的用户
 * user2 : 副评论对应的用户
 */
class MineComments(
    val newsInfo: NewsInfo? = null,
    val user: User? = null,
    val replyComment: CommentInfo? = null,
    val user2: User? = null
) {
}