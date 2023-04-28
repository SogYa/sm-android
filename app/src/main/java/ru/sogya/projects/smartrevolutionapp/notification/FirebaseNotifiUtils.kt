package ru.sogya.projects.smartrevolutionapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.screens.MainActivity
import kotlin.random.Random


class FirebaseNotifiUtils(private val context: Context) {

    fun showNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "firebaseNotify"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            .setSmallIcon(R.drawable.ic_baseline_firebase)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000,1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(
            channelId,
            "Firebase cloud notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }
}