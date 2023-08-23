package com.example.myapplication.api

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.entity.User

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.api
 * @ClassName : UserTask
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/11 14:34
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/11 14:34
 * @UpdateRemark : 更新说明
 */
interface UserTask {

    /**
     * 添加用户信息
     */
    fun insertUser(user: User)

    /**
     * 删除某个用户
     */
    fun deleteUser(user: User)

    /**
     * 查询所有用户信息
     */
    fun getUsers(): List<User>

    /**
     * 通过号码查询某个用户信息
     */
    fun queryUserByNumber(number: Long): User

    /**
     * 通过号码删除某个用户
     */
    fun deleteUserByNumber(number: Long)

    /**
     * 通过号码更新用户的密码
     */
    fun updatePass(number: Long, pass: String)

    /**
     *  通过号码更新用户信息
     */
    fun updateUserInfo(number: Long, neck: String, address: String, sign: String, imagePath: String)
}