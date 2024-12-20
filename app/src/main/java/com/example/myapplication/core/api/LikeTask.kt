package com.example.myapplication.core.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.LikeInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : LikeTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:28
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:28
 * @UpdateRemark : 更新说明
 */
interface LikeTask {

    /**
     * 添加点赞记录
     */
    fun insertLike(likeInfo: LikeInfo): Long

    /**
     * 删除点赞记录
     */
    fun deleteLike(likeInfo: LikeInfo)

    /**
     * 通过id删除点赞记录
     */
    fun deleteLikeById(id: Long)

    /**
     * 获取该number的点赞记录
     */
    fun getLikesMine(number: Long) : List<LikeInfo>

    /**
     * 获取该news的点赞记录
     */
    fun getLikesByNewId(newsId: Long) : List<LikeInfo>

    /**
     * 获取所有点赞记录
     */
    fun getAllLikes() : List<LikeInfo>
}