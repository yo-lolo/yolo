package com.example.myapplication.broadcastReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.blankj.utilcode.util.ActivityUtils
import com.example.myapplication.Constants
import com.example.myapplication.database.entity.NotifyInfo
import com.example.myapplication.getTag
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.ui.MarketActivity

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
        SpeedyLog.d(TAG, "onReceive接收到广播：$action")
        if (action == MESS_NOTIFY_ACTION) {
            val data = intent.getSerializableExtra("data") as NotifyInfo
            SpeedyLog.d(getTag(), data.toString())
            val intent = Intent(context, MarketActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("data", data)
            ActivityUtils.startActivity(intent)
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