package com.example.myapplication.core.repos

import com.example.myapplication.common.AppConfig
import com.example.myapplication.base.database.entity.User
import com.example.myapplication.core.imp.UserTaskImp
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
     * 通过号码更新当前用户的密码
     */
    suspend fun updatePass(pass: String) = withContext(Dispatchers.IO) {
        return@withContext userTaskImp.updatePass(AppConfig.phoneNumber, pass)
    }

    /**
     * 通过号码更新用户信息
     */
    suspend fun updateUserInfo(
        number: Long,
        neck: String,
        address: String,
        sign: String,
        imagePath: String
    ) =
        withContext(Dispatchers.IO) {
            return@withContext userTaskImp.updateUserInfo(number, neck, address, sign, imagePath)
        }
}