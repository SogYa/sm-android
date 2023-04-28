package ru.sogya.projects.smartrevolutionapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sogya.domain.utils.Constants.NOTIFICATION_MESSAGE
import com.sogya.domain.utils.Constants.NOTIFICATION_TITLE
import ru.sogya.projects.smartrevolutionapp.notification.FirebaseNotifiUtils
import ru.sogya.projects.smartrevolutionapp.receivers.FirebaseReceiver
import java.text.SimpleDateFormat
import java.util.*

class FirebaseCloudMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d(this.javaClass.name, "Message data payload: ${remoteMessage.notification.toString()}")

            // Get Message details
            val title = remoteMessage.notification?.title
            val message = remoteMessage.notification?.body

            // Check that 'Automatic Date and Time' settings are turned ON.
            // If it's not turned on, Return
            if (!isTimeAutomatic(applicationContext)) {
                Log.d(this.javaClass.name, "`Automatic Date and Time` is not enabled")
                return
            }

            // Check whether notification is scheduled or not
            val isScheduled = remoteMessage.notification
            Log.d("Show",isScheduled.toString())
            showNotification(title!!, message!!)
        }
    }

    private fun scheduleAlarm(
        scheduledTimeString: String?,
        title: String?,
        message: String?
    ) {
        val alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(applicationContext, FirebaseReceiver::class.java).let { intent ->
                intent.putExtra(NOTIFICATION_TITLE, title)
                intent.putExtra(NOTIFICATION_MESSAGE, message)
                PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

        // Parse Schedule time
        val scheduledTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(scheduledTimeString!!)

        scheduledTime?.let {
            // With set(), it'll set non repeating one time alarm.
            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                it.time,
                alarmIntent
            )
        }
    }

    private fun showNotification(title: String, message: String) {
        FirebaseNotifiUtils(applicationContext).showNotification(title, message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("onNewToken", token)
    }

    private fun isTimeAutomatic(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AUTO_TIME,
            0
        ) == 1
    }
}