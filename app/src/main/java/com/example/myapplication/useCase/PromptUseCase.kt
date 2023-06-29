package com.example.myapplication.useCase

import android.app.AlertDialog
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils


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

    fun deleteTodoPrompt(block: () -> Unit) {
        prompt("确定删除此待办事项？", {
            block.invoke()
        })
    }


    fun deleteAllAPkFilePrompt(block: () -> Unit) {
        prompt("确定清理全部安装包？", {
            block.invoke()
        })
    }

    fun deleteAPkFilePrompt(apkName: String, block: () -> Unit) {
        prompt("确定清理${apkName}应用的安装包？", {
            block.invoke()
        })
    }

    fun removeDownloadTaskPrompt(apkName: String, block: () -> Unit) {
        prompt("确定删除${apkName}的下载任务？", {
            block.invoke()
        })
    }

    fun igoreUpdatePrompt(apkName: String, block: () -> Unit) {
        prompt("确定忽略${apkName}的更新？", {
            block.invoke()
        })
    }

    fun removeImagePrompt(block: () -> Unit) {
        prompt("确认移除已添加图片吗？", {
            block.invoke()
        })
    }

    fun uninstallAppsPrompt(appNames: List<String>, block: () -> Unit) {
        if (appNames.isEmpty()) {
            ToastUtils.showShort("至少勾选一个应用")
            return
        }

        prompt("确定删除${appNames.size}个应用？", {
            block.invoke()
        })
    }

    fun uninstallAppPrompt(appName: String, block: () -> Unit) {
        prompt("是否卸载${appName}？", {
            block.invoke()
        })
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
        mPositiveButtonListener: () -> Unit,
        mNegativeButtonListener: () -> Unit = {}
    ) {
        val context = ActivityUtils.getTopActivity()
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("确定") { _, _ ->
                mPositiveButtonListener.invoke()
            }
            .setNegativeButton("取消") { _, _ ->
                mNegativeButtonListener.invoke()
            }
            .create()
            .show()
    }
}