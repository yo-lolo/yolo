package com.example.myapplication.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.util.Log
import com.example.myapplication.config.AppConfig

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
class EmergencyUnlockReceiver(context: Context, private val block: () -> Unit) :
    BroadcastReceiver() {

    private var audioManager: AudioManager? =
        context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager

    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "onReceive接收到广播：$action")
        if (action == VOLUME_CHANGED_ACTION && intent.getIntExtra(
                EXTRA_VOLUME_STREAM_TYPE,
                -1
            ) == AudioManager.STREAM_MUSIC
        ) {
            val currentVolume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)
            Log.i(TAG, "currentVolume：$currentVolume")
            if (currentVolume == 0) {
                block.invoke()
            }
        }
    }

    companion object {
        val TAG = AppConfig.BASE_TAG + EmergencyUnlockReceiver::class.java.simpleName
        var VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"
        var EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE"
    }
}