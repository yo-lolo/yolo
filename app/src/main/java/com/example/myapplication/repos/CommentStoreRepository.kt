package com.example.myapplication.repos

import com.example.myapplication.api.CommentTask
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : CommentStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/23 15:36
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/23 15:36
 * @UpdateRemark : 更新说明
 */
class CommentStoreRepository(private val commentTask: CommentTask) {

    suspend fun insertComment(
        newsId: Long,
        content: String,
        level: Int,
        replyNumber: Long? = null,
        replyId: Long? = null
    ): Long =
        withContext(Dispatchers.IO) {
            return@withContext commentTask.insertComment(
                CommentInfo(
                    newsId,
                    AppConfig.phoneNumber,
                    content,
                    TimeUtil.getCurrentMill(),
                    level,
                    replyNumber,
                    replyId
                )
            )
        }

    suspend fun deleteComment(commentInfo: CommentInfo) = withContext(Dispatchers.IO) {
        return@withContext commentTask.deleteComment(commentInfo)
    }

    suspend fun deleteCommentById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext commentTask.deleteCommentById(id)
    }

    suspend fun getAllComment(): List<CommentInfo> = withContext(Dispatchers.IO) {
        return@withContext commentTask.getAllComment()
    }

    suspend fun getCommentsByNewId(newsId: Long): List<CommentInfo> = withContext(Dispatchers.IO) {
        return@withContext commentTask.getCommentsByNewId(newsId)
    }

    suspend fun getCommentsByReplyId(replyId: Long): List<CommentInfo> =
        withContext(Dispatchers.IO) {
            return@withContext commentTask.getCommentsByReplyId(replyId)
        }

    suspend fun getCommentsByNumber(number: Long): List<CommentInfo> = withContext(Dispatchers.IO) {
        return@withContext commentTask.getCommentsByNumber(number)
    }

    suspend fun getCommentsById(id: Long): CommentInfo = withContext(Dispatchers.IO) {
        return@withContext commentTask.getCommentsById(id)
    }
}