package com.example.myapplication.repos

import com.example.myapplication.api.LikeTask
import com.example.myapplication.database.entity.LikeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : LikeStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/25 13:42
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/25 13:42
 * @UpdateRemark : 更新说明
 */
class LikeStoreRepository(private val likeTask: LikeTask) {

    /**
     * 添加点赞记录
     */
    suspend fun insertLike(likeInfo: LikeInfo): Long = withContext(Dispatchers.IO) {
        return@withContext likeTask.insertLike(likeInfo)
    }

    /**
     * 删除点赞记录
     */
    suspend fun deleteLike(likeInfo: LikeInfo) = withContext(Dispatchers.IO) {
        return@withContext likeTask.deleteLike(likeInfo)
    }

    /**
     * 通过id删除点赞记录
     */
    suspend fun deleteLikeById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext likeTask.deleteLikeById(id)
    }

    /**
     * 获取该number的点赞记录
     */
    suspend fun getLikesMine(number: Long): List<LikeInfo> = withContext(Dispatchers.IO) {
        return@withContext likeTask.getLikesMine(number)
    }

    /**
     * 获取该news的点赞记录
     */
    suspend fun getLikesByNewId(newsId: Long) : List<LikeInfo> = withContext(Dispatchers.IO) {
        return@withContext likeTask.getLikesByNewId(newsId)
    }

    /**
     * 获取所有点赞记录
     */
    suspend fun getAllLikes(): List<LikeInfo> = withContext(Dispatchers.IO) {
        return@withContext likeTask.getAllLikes()
    }
}