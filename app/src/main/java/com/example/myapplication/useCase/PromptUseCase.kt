package com.example.myapplication.useCase

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.R
import com.example.myapplication.util.layoutInflater


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.useCase
 * @ClassName : PromptUseCase
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 14:00
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 14:00
 * @UpdateRemark : 更新说明
 */
class PromptUseCase() {

    fun deletePrompt(data: String, block: () -> Unit) {
        prompt("确定要删掉${data}吗?") {
            block.invoke()
        }
    }


    fun removeImagePrompt(block: () -> Unit) {
        prompt("确认移除已添加图片吗？") {
            block.invoke()
        }
    }

    fun exitLoginPrompt(block: () -> Unit) {
        prompt("确定要退出登录吗?") {
            block.invoke()
        }
    }

    private fun prompt(
        view: View,
        mPositiveButtonListener: () -> Unit,
        mNegativeButtonListener: () -> Unit,
        onCancelListener: () -> Unit,
    ) {
        val context = ActivityUtils.getTopActivity()
        AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton("确定") { _, _ ->
                mPositiveButtonListener.invoke()
            }
            .setNegativeButton("取消") { _, _ ->
                mNegativeButtonListener.invoke()
            }
            .setOnCancelListener {
                onCancelListener.invoke()
            }
            .create()
            .show()
    }

    private fun prompt(
        message: String,
        mPositiveButtonListener: () -> Unit
    ) {

        val context = ActivityUtils.getTopActivity()
        val customView = context.layoutInflater().inflate(R.layout.normal_alert_layout, null, false)

        val alertDialog = AlertDialog.Builder(context)
            .setView(customView)
            .setCancelable(false)
            .create()
        val alertOk = customView.findViewById<TextView>(R.id.alert_ok)
        val alertCancel = customView.findViewById<TextView>(R.id.alert_cancel)
        val alertDesc = customView.findViewById<TextView>(R.id.alert_desc)
        alertDesc.text = message
        alertOk.setOnClickListener {
            mPositiveButtonListener.invoke()
            alertDialog.dismiss()
        }
        alertCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window!!.decorView.setPadding(70, 0, 70, 0)

    }
}