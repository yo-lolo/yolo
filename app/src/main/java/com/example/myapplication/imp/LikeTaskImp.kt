package com.example.myapplication.imp

import com.example.myapplication.api.LikeTask
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.dao.LikeDao
import com.example.myapplication.database.entity.LikeInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : LikeTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 16:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 16:30
 * @UpdateRemark : 更新说明
 */
class LikeTaskImp(appDataBase: AppDataBase, private val likeDao: LikeDao = appDataBase.LikeDao()) :
    LikeTask {
    override fun insertLike(likeInfo: LikeInfo): Long {
        return likeDao.insertLike(likeInfo)
    }

    override fun deleteLike(likeInfo: LikeInfo) {
        likeDao.deleteLike(likeInfo)
    }

    override fun deleteLikeById(id: Long) {
        likeDao.deleteLikeById(id)
    }

    override fun getLikesMine(number: Long): List<LikeInfo> {
        return likeDao.getLikesMine(number)
    }

    override fun getLikesByNewId(newsId: Long): List<LikeInfo> {
        return likeDao.getLikesByNewId(newsId)
    }

    override fun getAllLikes(): List<LikeInfo> {
        return likeDao.getAllLikes()
    }
}