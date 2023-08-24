package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.CommentInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.dao
 * @ClassName : CommentDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:08
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:08
 * @UpdateRemark : 更新说明
 */
@Dao
interface CommentDao {

    @Insert
    fun insertComment(commentInfo: CommentInfo): Long

    @Delete
    fun deleteComment(commentInfo: CommentInfo)

    @Query("delete from CommentInfo where id = :id")
    fun deleteCommentById(id: Long)

    @Query("select * from CommentInfo")
    fun getAllComment(): List<CommentInfo>

    @Query("select * from CommentInfo where newsId = :newsId")
    fun getCommentsByNewId(newsId: Long): List<CommentInfo>

    @Query("select * from CommentInfo where number = :number")
    fun getCommentsById(number: Long): List<CommentInfo>

}