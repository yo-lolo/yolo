package com.example.myapplication.core.imp

import com.example.myapplication.core.api.CommentTask
import com.example.myapplication.base.database.AppDataBase
import com.example.myapplication.base.database.dao.CommentDao
import com.example.myapplication.base.database.entity.CommentInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : CommentTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:30
 * @UpdateRemark : 更新说明
 */
class CommentTaskImp(
    appDataBase: AppDataBase,
    private val commentDao: CommentDao = appDataBase.CommentDao()
) : CommentTask {
    override fun insertComment(commentInfo: CommentInfo): Long {
        return commentDao.insertComment(commentInfo)
    }

    override fun deleteComment(commentInfo: CommentInfo) {
        commentDao.deleteComment(commentInfo)
    }

    override fun deleteCommentById(id: Long) {
        commentDao.deleteCommentById(id)
    }

    override fun getAllComment(): List<CommentInfo> {
        return commentDao.getAllComment()
    }

    override fun getCommentsByNewId(newsId: Long): List<CommentInfo> {
        return commentDao.getCommentsByNewId(newsId)
    }

    override fun getCommentsByNumber(number: Long): List<CommentInfo> {
        return commentDao.getCommentsByNumber(number)
    }

    override fun getCommentsById(id: Long): CommentInfo {
        return commentDao.getCommentsById(id)
    }

    override fun getCommentsByReplyId(replyId: Long): List<CommentInfo> {
        return commentDao.getCommentsByReplyId(replyId)
    }

    override fun updateCommentType(type: Int, id: Long) {
        return commentDao.updateCommentType(type, id)
    }

}