package com.example.myapplication.shareLifeUser.vm

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.base.baseUi.BaseViewModel

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
    val listData = MutableLiveData<List<String>>()
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

        }
    }
}