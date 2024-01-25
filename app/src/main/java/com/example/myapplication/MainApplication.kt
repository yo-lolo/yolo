package com.example.myapplication

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.log.SpeedyLogConfig
import com.tencent.mmkv.MMKV

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication
 * @ClassName : MainApplication
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 11:31
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 11:31
 * @UpdateRemark : 更新说明
 */

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)
        MMKV.initialize(this)
    }

    fun init(context: Context) {
        DataManager.init(context)
        initSpeedyLog(context)
    }

    private fun initSpeedyLog(appContext: Context) {
        //写入拦截,可自定义写入/上传操作
        val speedyLogConfig = SpeedyLogConfig.Build(appContext)
            .path(appContext.getExternalFilesDir(null)?.absolutePath + "/" + Constants.LOG_PATH) //日志目录,一般不要动安卓10限制了外部目录访问了
            .buffSize(128 * 1024) //buff大小
            .delay(100) //延迟写入时间
            .day(30) //日志保留30天,默认无限制
            .methodCount(1) //打印调用方法名 大于0支持打印调用方法栈
            .debug(true) //true会输出控制台,上线可关掉
            .writeData { _, _, _ ->
                false //false会继续执行写入, true不继续执行
            }
            .build()
        SpeedyLog.init(speedyLogConfig)
    }

}

fun Any.getTag() = Constants.BASE_TAG + this.javaClass.simpleName

/**
 * 是否登录
 */
fun Any.isLogin() = MMKVManager.isLogin

/**
 * 是否通知
 */
fun Any.isNotify() = MMKVManager.isNotify

/**
 * 判断是否提示请先登录
 */
fun Any.chargeToastLogin(listener: () -> Unit) = if (isLogin()) listener else ToastUtils.showShort("请先登录")