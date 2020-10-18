package com.example.noci.notification

import android.R
import android.app.*
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import com.example.noci.MainActivity


class MyNewIntentService(application: Application) : AndroidViewModel(application) {
    private lateinit var builder: NotificationCompat.Builder

    private var CHANNEL_ID = "1"
    private val context = getApplication<Application>().applicationContext

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(getApplication(), MainActivity::class.java)
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