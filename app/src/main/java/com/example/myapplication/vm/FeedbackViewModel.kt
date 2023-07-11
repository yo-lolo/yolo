package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : FeedbackViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 17:25
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 17:25
 * @UpdateRemark : 更新说明
 */
class FeedbackViewModel : ViewModel() {

    var type = MutableLiveData(-1)
    val desc = MutableLiveData("")

    fun onSubmit(pictureItems: List<String>) {

    }

    fun typeChange() {

    }
}