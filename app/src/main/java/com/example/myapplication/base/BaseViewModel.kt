package com.example.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.getTag
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.util.getLoadingPopup
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.base
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/2/27 16:32
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/2/27 16:32
 * @UpdateRemark : 更新说明
 */
open class BaseViewModel : ViewModel() {
    /**
     * 当前加载任务计数
     */
    val loadingTaskCount = MutableLiveData(0)

    fun launchSafe(progress: Boolean = true, block: suspend () -> Unit) {
        launch(progress) {
            kotlin.runCatching {
                block.invoke()
            }.onFailure {
                if (it is IOException) {
                    ToastUtils.showShort("网络连接失败，请稍后再试")
                }
                SpeedyLog.d(getTag(), it.toString())
            }
        }
    }

    fun launchTest(progress: Boolean = true, block: suspend () -> Unit) {
        var time : Long = 0
        var endTime : Long = 0
        launch(progress) {
            kotlin.runCatching {
                time = TimeUtils.getNowMills()
                block.invoke()
            }.onFailure {
                if (it is IOException) {
                    ToastUtils.showShort("网络连接失败，请稍后再试")
                }
                SpeedyLog.d("", it.toString())
            }.onSuccess {
                endTime = TimeUtils.getNowMills()
                SpeedyLog.d("time_during", "${endTime - time}")
            }
        }
    }

    fun launch(progress: Boolean = true, block: suspend () -> Unit) {
        viewModelScope.launch {
            if (progress) {
                loadingTaskCount.value = loadingTaskCount.value!! + 1
                val a = async {
                    delay(500)
                    0
                }

                val b = async {
                    block.invoke()
                    0
                }

                a.await() + b.await()

                loadingTaskCount.value = loadingTaskCount.value!! - 1
            } else {
                block.invoke()
            }

        }
    }

    private val loadingPopup = getLoadingPopup()

    /**
     * 显示加载
     */
    protected fun showLoading() {
        runOnUiThread {
            loadingPopup?.show()
        }
    }

    /**
     * 解除加载
     */
    protected fun dismissLoading() {
        runOnUiThread {
            loadingPopup?.dismiss()
        }
    }
}