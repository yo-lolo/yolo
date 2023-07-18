package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.entity.ApkInfo
import com.example.myapplication.useCase.AppListUseCase
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : DetailOneViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 15:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 15:49
 * @UpdateRemark : 更新说明
 */
class DetailTwoViewModel : BaseViewModel() {

    //当前所选的type数据加载到页码
    private var page = 1
    //单次下拉获取的数量
    private val limit = 10
    //app展示数据
    val listData = MutableLiveData<List<ApkInfo>>()
    //是否可以加载更多
    val canLoadMoreState = MutableLiveData(false)


    fun onRefresh() {
        page = 1
        getDataFromService()
    }

    fun onLoadMore() {
        page++
        getDataFromService()
    }

    private fun getDataFromService() {
        launchSafe {

            val apps = AppListUseCase().getTypeApks(
                page = page.toString(),
                limit = limit.toString()
            )
            canLoadMoreState.value = apps.size == limit

            val data = mutableListOf<ApkInfo>()
            if (page != 1 && listData.value != null) {
                data.addAll(listData.value!!)
            }
            data.addAll(apps)
            listData.value = data
        }
    }
}