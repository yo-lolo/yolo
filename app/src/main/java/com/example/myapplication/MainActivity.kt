package com.example.myapplication

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.MarketActivity

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        toMyApplicationPage()
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
             *
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
        val TAG = MainActivity::class.java.simpleName
    }
}