package com.example.noci.notification

import android.app.Application
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.noci.MainActivity


class AlarmReceiver : BroadcastReceiver() {
    private lateinit var builder: NotificationCompat.Builder

    private var CHANNEL_ID = "1"
    //private val context = getApplication<Application>().applicationContext

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("You wanna hear a secret?")
                .setContentText("Then just click on me!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
            val notification = builder.build()
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(0, notification)
        }
    }
}