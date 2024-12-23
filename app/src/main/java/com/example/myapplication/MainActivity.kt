package com.example.myapplication

import android.Manifest
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.example.myapplication.base.baseUi.BaseActivity
import com.example.myapplication.common.Constants
import com.example.myapplication.base.cache.MMKVManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.base.log.SpeedyLog
import com.example.myapplication.shareLifeUser.MarketActivity
import com.example.myapplication.util.PromptUtils
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initView()
        toMyApplicationPage()
    }

    private fun initView() {
        //欢迎语动画设置
        ValueAnimator.ofFloat(0.0F, 1.0F).also { animator ->
            animator.repeatMode = ValueAnimator.RESTART
            animator.duration = 2000L
            animator.addUpdateListener {
                activityMainBinding.typerTextView.setProgress(it.animatedValue as Float)
            }
        }.start()
    }

    /**
     * 根据自动登录标识跳转到指定页面
     */
    private fun toMyApplicationPage() {
        checkPermissions(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            /**
             * 权限check完成后 在协程内依次执行操作 --- 即在弹窗展示完成后，跳转到指定页面，并结束当前页面
             */
            lifecycleScope.launch {
                if (MMKVManager.isShowAppIntroductionDialog) {
                    PromptUtils().showAppIntroductionDialog(
                        Constants.APP_INTRODUCTION_DIALOG_TITLE,
                        Constants.APP_INTRODUCTION_DIALOG_CONTENT
                    ) {
                        MMKVManager.isShowAppIntroductionDialog = false
                    }
                }
                ActivityUtils.startActivity(MarketActivity::class.java)
                finish()
            }
        }
    }

    /**
     * 检查权限
     */
    private fun checkPermissions(
        permissions: Array<String>,
        listener: () -> Unit
    ) {
        PermissionUtils.permission(*permissions)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    listener.invoke()
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    if (deniedForever.size > 0) {
                        SpeedyLog.d(TAG, "权限被永久拒绝，即将跳转权限设置界面，同意后可以正常使用")
                        Handler().postDelayed({ // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            PermissionUtils.launchAppDetailsSettings()
                        }, 1000)
                        SpeedyLog.d(TAG, deniedForever.toString() + "权限被永久拒绝，请前往设置界面允许权限")
                    } else {
                        SpeedyLog.d(
                            TAG,
                            denied.toString() + "权限获取失败"
                        )
                    }
                }
            })
            .request()

    }

    companion object {
        val TAG: String = getTag()
    }
}