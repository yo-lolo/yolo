package com.example.myapplication

import android.Manifest
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.ui.MarketActivity
import com.example.myapplication.useCase.PromptUseCase
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initView()
        toMyApplicationPage()

//        val uri = Uri.parse("content://com.example.myapplication.provider.BookProvider")
//        contentResolver.query(uri, null, null, null, null, null)
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
                    PromptUseCase().showAppIntroductionDialog(
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
                        Toast.makeText(
                            MainActivity@ this as Context,
                            "权限被永久拒绝，即将跳转权限设置界面，同意后可以正常使用",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({ // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            PermissionUtils.launchAppDetailsSettings()
                        }, 1000)
                        SpeedyLog.d(
                            TAG,
                            deniedForever.toString() + "权限被永久拒绝，请前往设置界面允许权限"
                        )
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