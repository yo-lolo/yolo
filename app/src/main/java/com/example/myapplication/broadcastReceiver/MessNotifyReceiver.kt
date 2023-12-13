package com.example.myapplication.broadcastReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.Constants
import com.example.myapplication.data.NotifyData

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.reciver
 * @ClassName : EmergencyUnlockReceiver
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/7 14:20
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/7 14:20
 * @UpdateRemark : 更新说明
 */
class MessNotifyReceiver(context: Context) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "onReceive接收到广播：$action")
        if (action == MESS_NOTIFY_ACTION){
            val data = intent.getSerializableExtra("data") as NotifyData
            ToastUtils.showShort(data.toString())
        }
    }

    companion object {
        /**
         * 注册监听
         */
        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        fun registerMessNotifyReceiver(
            context: Context
        ) {
            context.registerReceiver(
                MessNotifyReceiver(context), IntentFilter().apply {
                    // 添加自定义 action
                    addAction(MESS_NOTIFY_ACTION)
                })
        }
        /**
         * 消息通知 Action
         */
        const val MESS_NOTIFY_ACTION = "android.intent.action.MESS_NOTIFY"
        val TAG = Constants.BASE_TAG + MessNotifyReceiver::class.java.simpleName
    }
}