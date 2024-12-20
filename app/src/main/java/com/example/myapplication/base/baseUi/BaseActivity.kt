package com.example.myapplication.base.baseUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.base
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/1/10 11:29
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/1/10 11:29
 * @UpdateRemark : 更新说明
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 沉浸式状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(
                true,
                0.2f
            ) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
            .init()
    }
}