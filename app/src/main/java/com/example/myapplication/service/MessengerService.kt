package com.example.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import com.example.myapplication.Constants

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.service
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/9/18 11:09
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/9/18 11:09
 * @UpdateRemark : 更新说明
 */
class MessengerService : Service() {

    class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constants.MSG_FROM_CLIENT -> {
                    Log.e(Constants.BASE_TAG, msg.data.getString("msg").toString())
                    val client = msg.replyTo
                    val replyMessage = Message.obtain(null, Constants.MSG_FROM_SERVICE)
                    val bundle = Bundle()
                    bundle.putString("reply", "嗯，你的消息我收到了，稍后会回复你。")
                    replyMessage.data = bundle
                    try {
                        client.send(replyMessage)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }

                else -> super.handleMessage(msg)
            }
        }
    }

    private final val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger.binder
    }
}