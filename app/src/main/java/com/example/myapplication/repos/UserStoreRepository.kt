package com.example.myapplication.repos

import com.example.myapplication.database.entity.User
import com.example.myapplication.imp.UserTaskImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.repos
 * @ClassName : UserStoreRepository
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/11 16:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/11 16:04
 * @UpdateRemark : 更新说明
 */
class UserStoreRepository(private val userTaskImp: UserTaskImp) {

    /**
     * 添加用户信息
     */
    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.insertUser(user)
    }

    /**
     * 删除某个用户
     */
    suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.deleteUser(user)
    }

    /**
     * 查询所有用户信息
     */
    suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.getUsers()
    }

    /**
     * 通过号码查询某个用户信息
     */
    suspend fun queryUserByNumber(number: Long): User = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.queryUserByNumber(number)
    }

    /**
     * 通过号码删除某个用户
     */
    suspend fun deleteUserByNumber(number: Long) = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.deleteUserByNumber(number)
    }

    /**
     * 通过号码更新用户的密码
     */
    suspend fun updatePass(number: Long, pass: String) = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.updatePass(number, pass)
    }
}