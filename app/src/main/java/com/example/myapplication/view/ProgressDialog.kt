package com.example.myapplication.view

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.myapplication.R

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : ProgressDialog
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 11:34
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 11:34
 * @UpdateRemark : 更新说明
 */
class ProgressDialog(
    context: Context
) : Dialog(context, R.style.BaseDialogTheme) {

    /** 背景遮盖层开关 */
    private var backgroundDimEnabled: Boolean = true

    /** 背景遮盖层透明度 */
    private var backgroundDimAmount: Float = 0.5f

    var messageText: TextView

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        setContentView(R.layout.wait_dialog)

        messageText = findViewById(R.id.tv_wait_message)


        val window: Window? = window
        if (window != null) {
            val params: WindowManager.LayoutParams = window.attributes
            if (backgroundDimEnabled) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                window.setDimAmount(backgroundDimAmount)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            window.attributes = params
        }
    }

    fun setMessage(msg: String) {
        messageText.text = msg
    }

}