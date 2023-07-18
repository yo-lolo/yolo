package com.example.myapplication.useCase

import com.example.myapplication.database.entity.ApkInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.useCase
 * @ClassName : AppListUseCase
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 15:30
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 15:30
 * @UpdateRemark : 更新说明
 */
class AppListUseCase {

    fun getTypeApks(page: String, limit: String): List<ApkInfo> {
        // TODO 根据page和limit获取所需要的数据
        val list = mutableListOf<ApkInfo>()
        repeat((1..20).random()) {
            list.add(
                ApkInfo(
                    222,
                    "haha haha",
                    iconUrl,
                    "测试应用2",
                    "这里是对APP的一段描述",
                )
            )
        }
        list[0].desc = "111"
        return list
    }

    companion object {
        val iconUrl =
            "https://alipic.lanhuapp.com/SketchPng0be9ed0b666e3719494ebff9d1336a55e7fb52bce61eccdc3e1e078960601772"
    }
}