package com.example.myapplication.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.FeedbackInfo
import com.example.myapplication.util.JsonUtil
import com.example.myapplication.util.TimeUtil

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
class FeedbackViewModel : BaseViewModel() {

    val commentSuccess = MutableLiveData(false)
    var type = MutableLiveData("0")
    private val feedbackStoreRepository = DataManager.feedbackStoreRepository

    fun onSubmit(pictureItems: List<String>, desc: String) {
        val jsonPicture = JsonUtil.toJson(pictureItems)
        if (!type.value.equals("0") && desc.isNotEmpty() && pictureItems.isNotEmpty()) {
            launchSafe {
                kotlin.runCatching {
                    feedbackStoreRepository.insertFeedBack(
                        FeedbackInfo(
                            AppConfig.phoneNumber,
                            type.value!!,
                            desc,
                            TimeUtil.getCurrentMill(),
                            jsonPicture
                        )
                    )
                }.onSuccess {
                    ToastUtils.showShort("反馈成功")
                    commentSuccess.value = true
                }.onFailure {
                    ToastUtils.showShort("反馈失败")
                }
            }
        } else {
            ToastUtils.showShort("请完成反馈填写")
        }
    }

    fun typeChange(typeId: String) {
        type.value = typeId
    }
}