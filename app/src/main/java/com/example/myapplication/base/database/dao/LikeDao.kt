package com.example.myapplication.base.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.LikeInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.dao
 * @ClassName : LikeDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 14:09
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 14:09
 * @UpdateRemark : 更新说明
 */
@Dao
interface LikeDao {
    @Insert
    fun insertLike(likeInfo: LikeInfo): Long

    @Delete
    fun deleteLike(likeInfo: LikeInfo)

    @Query("delete from LikeInfo where id = :id")
    fun deleteLikeById(id: Long)

    @Query("select * from LikeInfo where number = :number")
    fun getLikesMine(number: Long): List<LikeInfo>

    @Query("select * from LikeInfo where newsId = :newsId")
    fun getLikesByNewId(newsId: Long): List<LikeInfo>

    @Query("select * from LikeInfo")
    fun getAllLikes(): List<LikeInfo>

}