package com.example.myapplication.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.database.Dao
 * @ClassName : UserDao
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/10 14:40
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/10 14:40
 * @UpdateRemark : 更新说明
 */

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Delete()
    fun deleteUser(user: User)

    @Query("select * from User")
    fun getUsers(): List<User>

    @Query("select * from User where number = :number ")
    fun queryUserByNumber(number: Int): User

    @Query("delete from User where number = :number")
    fun deleteUserByNumber(number: Int)

    @Query("update user set pass = :pass where number = :number")
    fun updatePass(number: Int, pass: String)
}