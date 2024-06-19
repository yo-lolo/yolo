package com.example.myapplication

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.log.SpeedyLogConfig
import com.example.myapplication.util.AppInfoUtils
import com.tencent.mmkv.MMKV
import xcrash.ICrashCallback
import xcrash.XCrash
import xcrash.XCrash.InitParameters

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
        initXCrash(context)
    }

    private fun initSpeedyLog(appContext: Context) {
        //写入拦截,可自定义写入/上传操作
        val speedyLogConfig = SpeedyLogConfig.Build(appContext)
            .path(appContext.getExternalFilesDir(null)?.absolutePath + Constants.LogFilesDir) //日志目录,一般不要动安卓10限制了外部目录访问了
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

    private fun initXCrash(appContext: Context) {
        // callback for java crash, native crash and ANR
        val callback = ICrashCallback { logPath: String?, emergency: String? ->
            SpeedyLog.d(
                getTag(),
                "log path: " + (logPath ?: "(null)") + ", emergency: " + (emergency ?: "(null)")
            )
            if (emergency != null) {
                //在磁盘耗尽时保存基本崩溃信息的缓冲区。
                //发送到服务器日志
            }
        }

        // Initialize xCrash.
        XCrash.init(
            appContext, InitParameters() // 通用配置
                .setAppVersion(AppInfoUtils.getAppVersionName(appContext))
                .setPlaceholderCountMax(3)
                .setPlaceholderSizeKb(512)
                .setLogDir(appContext.getExternalFilesDir(null)?.absolutePath + Constants.CrashFilesDir)
                .setLogFileMaintainDelayMs(1000) // Java 崩溃配置
                .setJavaRethrow(true)
                .setJavaLogCountMax(10)
                .setJavaDumpAllThreadsWhiteList(
                    arrayOf<String>("^main$", "^Binder:.*", ".*Finalizer.*")
                )
                .setJavaDumpAllThreadsCountMax(10)
                .setJavaCallback(callback) // Native 崩溃配置
                .setNativeRethrow(true)
                .setNativeLogCountMax(10)
                .setNativeDumpAllThreadsWhiteList(
                    arrayOf<String>(
                        "^xcrash\\.sample$",
                        "^Signal Catcher$",
                        "^Jit thread pool$",
                        ".*(R|r)ender.*",
                        ".*Chrome.*"
                    )
                )
                .setNativeDumpAllThreadsCountMax(10)
                .setNativeCallback(callback) // ANR 配置
                .setAnrRethrow(true)
                .setAnrLogCountMax(10)
                .setAnrCallback(callback)
        )
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
fun Any.chargeToastLogin(listener: () -> Unit) =
    if (isLogin()) listener.invoke() else ToastUtils.showShort("请先登录")