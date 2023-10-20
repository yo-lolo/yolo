package com.example.myapplication.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import com.example.myapplication.Constants
import com.example.myapplication.base.BaseActivity

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
class MessengerActivity : BaseActivity() {

    var mService: Messenger? = null
    private val mGetReplyMessenger = Messenger(MessengerHandler())

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(classNane: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            val msg = Message.obtain(null, Constants.MSG_FROM_CLIENT)
            val data = Bundle()
            data.putString("msg", "hello, this is client .")
            msg.data = data
            msg.replyTo = mGetReplyMessenger
            try {
                mService?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
        }
    }

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constants.MSG_FROM_SERVICE -> {
                    Log.e(Constants.BASE_TAG, msg.data.getString("reply").toString())
                }

                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }
}