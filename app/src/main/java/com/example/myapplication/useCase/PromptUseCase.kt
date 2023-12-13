package com.example.myapplication.useCase

import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.R
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.layoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.useCase
 * @ClassName : PromptUseCase
 * @Description : 弹窗工具类
 * @Author : yulu
 * @CreateDate : 2023/6/29 14:00
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 14:00
 * @UpdateRemark : 更新说明
 */
class PromptUseCase() {

    /**
     * 删除弹窗
     */
    fun deletePrompt(data: String, block: () -> Unit) {
        prompt("确定要删掉${data}吗?") {
            block.invoke()
        }
    }

    /**
     * 移除图片弹窗
     */
    fun removeImagePrompt(block: () -> Unit) {
        prompt("确认移除已添加图片吗？") {
            block.invoke()
        }
    }

    /**
     * 退出登陆弹窗
     */
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

    fun prompt(
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

    /**
     * 大图片展示弹窗
     */
    fun promptBigImage(
        path: String
    ) {
        val context = ActivityUtils.getTopActivity()
        val customView = context.layoutInflater().inflate(R.layout.big_image_layout, null, false)

        val alertDialog = AlertDialog.Builder(context)
            .setView(customView)
            .setCancelable(false)
            .create()
        val bigImage = customView.findViewById<ImageView>(R.id.big_image)
        GlideImageLoader().displayLocalFile(path, bigImage)

        bigImage.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window!!.decorView.setPadding(0, 30, 0, 50)
    }

    /**
     * 审核对话框
     */
    fun batchAuditPrompt(comments: List<Long>, block: () -> Unit) {
        if (comments.isEmpty()) {
            ToastUtils.showShort("至少勾选一个评论")
            return
        }
        prompt("确定审核通过${comments.size}个评论？") {
            block.invoke()
        }
    }

    /**
     * 展示App简介弹窗
     */
    suspend fun showAppIntroductionDialog(
        title: String,
        content: String,
        doNotAskAgainListener: () -> Unit
    ): Unit =
        suspendCancellableCoroutine { continuation ->
            val context = ActivityUtils.getTopActivity()
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("我知道了") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("不再提醒") { dialog, _ ->
                    doNotAskAgainListener.invoke()
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    continuation.resume(Unit)
                }
                .show()
        }

}