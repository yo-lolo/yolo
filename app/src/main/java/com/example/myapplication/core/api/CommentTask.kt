package com.example.myapplication.core.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.CommentInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : CommentTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:28
 * @UpdateRemark : 更新说明
 */
interface CommentTask {

    /**
     * 添加评论
     */
    fun insertComment(commentInfo: CommentInfo): Long

    /**
     * 删除评论
     */
    fun deleteComment(commentInfo: CommentInfo)

    /**
     * 通过id 删除评论
     */
    fun deleteCommentById(id: Long)

    /**
     * 获取所有评论
     */
    fun getAllComment(): List<CommentInfo>

    /**
     * 通过文章Id获取评论
     */
    fun getCommentsByNewId(newsId: Long): List<CommentInfo>

    /**
     * 查找该用户的所有评论
     */
    fun getCommentsByNumber(number: Long): List<CommentInfo>

    /**
     * 根据Id查找评论
     */
    fun getCommentsById(id: Long): CommentInfo

    /**
     * 找回复的所有评论
     */
    fun getCommentsByReplyId(replyId: Long): List<CommentInfo>

    /**
     * 更新评论审核状态
     */
    fun updateCommentType(type: Int, id: Long)

}