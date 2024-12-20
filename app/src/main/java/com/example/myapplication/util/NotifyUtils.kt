package com.example.myapplication.util


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.receiver.MessNotifyReceiver
import com.example.myapplication.base.database.entity.NotifyInfo
import com.example.myapplication.databinding.LayoutEmergencyNotifyItemBinding


class NotifyUtils {

    private val context = DataManager.context
    private lateinit var binding: LayoutEmergencyNotifyItemBinding
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    /**
     * 显示到通知栏
     *
     */
    fun showNotify(notifyData: NotifyInfo) {
        binding = LayoutEmergencyNotifyItemBinding.inflate(context.layoutInflater())

        // 自定义通知布局
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.layout_emergency_notify_item)
        //给通知布局中的组件设置点击事件
        notificationLayout.setTextViewText(R.id.notify_title, notifyData.title)
        notificationLayout.setTextViewText(R.id.notify_content, notifyData.content)
        // Intent设置自定义的Action、传入data
        val intentWithAction = Intent().setAction(MessNotifyReceiver.MESS_NOTIFY_ACTION)
        intentWithAction.putExtra("data", notifyData)
        intentWithAction.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(context, 0, intentWithAction, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, 0, intentWithAction, PendingIntent.FLAG_ONE_SHOT)
        }

        // 构建自定义通知布局
        var notifyBuild: NotificationCompat.Builder? = null
        val channelId = context.packageName
        // 构建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    channelId,
                    "FISH-APP通知",
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            // 是否在久按桌面图标时显示此渠道的通知
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)
            notifyBuild = NotificationCompat.Builder(context, channelId)
            notifyBuild.setChannelId(channelId);

        } else {
            notifyBuild = NotificationCompat.Builder(context)
        }

        notifyBuild.setSmallIcon(R.mipmap.notify_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout) //设置自定义通知布局
            .setPriority(NotificationCompat.PRIORITY_HIGH) //设置优先级
            .setAutoCancel(true) //设置点击后取消Notification
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent) //设置跳转
            .build()
        notificationManager.notify(1, notifyBuild.build())

    }

}