package com.example.myapplication.base.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.base.database.entity.NotifyInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.database.dao
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/12/19 10:42
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/12/19 10:42
 * @UpdateRemark : 更新说明
 */
@Dao
interface NotifyDao {

    @Insert
    fun insertNotifyInfo(notifyInfo: NotifyInfo): Long

    @Query("delete from NotifyInfo where id =:id")
    fun deleteNotifyById(id: Long)
}