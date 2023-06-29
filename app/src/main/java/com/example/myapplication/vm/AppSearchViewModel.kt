package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.entity.ApkInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : AppSeachViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:09
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:09
 * @UpdateRemark : 更新说明
 */
class AppSearchViewModel : ViewModel() {

    /**
     * 当前加载任务计数
     */
    val loadingTaskCount = MutableLiveData(0)

    /**
     * 查询结果
     */
    val searchResult = MutableLiveData<List<ApkInfo>>()

    fun searchApp(name: String) {

    }
}