package com.example.myapplication.core.imp

import com.example.myapplication.core.api.UserTask
import com.example.myapplication.base.database.AppDataBase
import com.example.myapplication.base.database.dao.UserDao
import com.example.myapplication.base.database.entity.User
import com.example.myapplication.base.database.entity.UserUpdate

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.imp
 * @ClassName : UserTaskImp
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/11 14:38
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/11 14:38
 * @UpdateRemark : 更新说明
 */
class UserTaskImp(
    private val appDataBase: AppDataBase,
    private val userDao: UserDao = appDataBase.userDao()
) : UserTask {

    override fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    override fun queryUserByNumber(number: Long): User {
        return userDao.queryUserByNumber(number)
    }

    override fun deleteUserByNumber(number: Long) {
        userDao.deleteUserByNumber(number)
    }

    override fun updatePass(number: Long, pass: String) {
        userDao.updatePass(number, pass)
    }

    override fun updateUserInfo(
        number: Long, neck: String, address: String, sign: String, imagePath: String
    ) {
        userDao.updateUserInfo(UserUpdate(number, neck, address, sign, imagePath))
    }
}