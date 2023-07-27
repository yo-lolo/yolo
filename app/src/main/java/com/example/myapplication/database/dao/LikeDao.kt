package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.myapplication.database.entity.LikeInfo

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

}