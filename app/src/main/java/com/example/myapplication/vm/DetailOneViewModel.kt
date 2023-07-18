package com.example.myapplication.vm

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : DetailOneViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 13:36
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 13:36
 * @UpdateRemark : 更新说明
 */
class DetailOneViewModel : BaseViewModel() {

    fun insertOnce(name: String, desc: String) : Boolean {
        return if (name.isNotEmpty()&&desc.isNotEmpty()){
            viewModelScope.launch {
                // TODO insert ONCE
            }
            true
        }else{
            ToastUtils.showShort("请完成内容填写")
            false
        }

    }
}