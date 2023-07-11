package com.example.myapplication.imp

import com.example.myapplication.api.UserTask
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.Dao.UserDao
import com.example.myapplication.database.entity.User

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

    override fun queryUserByNumber(number: Int): User {
        return userDao.queryUserByNumber(number)
    }

    override fun deleteUserByNumber(number: Int) {
        userDao.deleteUserByNumber(number)
    }

    override fun updatePass(number: Int, pass: String) {
        userDao.updatePass(number, pass)
    }
}