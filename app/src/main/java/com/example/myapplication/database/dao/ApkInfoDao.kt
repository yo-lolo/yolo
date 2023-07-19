package com.example.myapplication.database.dao

import androidx.room.*
import com.example.myapplication.database.entity.ApkInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.Dao
 * @ClassName : ApkInfoDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:39
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:39
 * @UpdateRemark : 更新说明
 */
@Dao
interface ApkInfoDao {
    @Insert
    fun insertApkInfo(apkInfo: ApkInfo): Long

    @Update
    fun updateApkInfo(apkInfo: ApkInfo)

    @Query("delete from ApkInfo")
    fun clearAll()

    @Delete
    fun deleteApkInfo(apkInfo: ApkInfo)

    @Query("select * from ApkInfo")
    fun getAllApkInfos(): List<ApkInfo>

    @Query("select * from ApkInfo order by id desc limit 1")
    fun getLastInsertApkInfo(): ApkInfo
}