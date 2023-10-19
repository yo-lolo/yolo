package com.example.myapplication

import android.Manifest
import android.animation.ValueAnimator
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.MarketActivity

class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initView()
        toMyApplicationPage()

        val uri = Uri.parse("content://com.example.myapplication.provider.BookProvider")
        contentResolver.query(uri, null, null, null, null, null)
    }

    private fun initView(){
        //欢迎语动画设置
        ValueAnimator.ofFloat(0.0F, 1.0F).also { animator ->
            animator.repeatMode = ValueAnimator.RESTART
            animator.duration = 2000L
            animator.addUpdateListener {
                activityMainBinding.typerTextView.setProgress(it.animatedValue as Float)
            }
        }.start()
    }

    private fun toMyApplicationPage() {
        checkPermissions(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN
            )
        ) {
            /**
             * 权限check完成后 执行如下： 1.通过自动登录标识 判断是否要进入登录界面
             */
            ActivityUtils.startActivity(MarketActivity::class.java)
            finish()
        }
    }

    private fun checkPermissions(
        permisssions: Array<String>,
        listener: () -> Unit
    ) {
        PermissionUtils.permission(*permisssions)
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
                        Log.i(
                            TAG,
                            deniedForever.toString() + "权限被永久拒绝，请前往设置界面允许权限"
                        )
                    } else {
                        Log.i(
                            TAG,
                            denied.toString() + "权限获取失败"
                        )
                    }
                }
            })
            .request()


    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}