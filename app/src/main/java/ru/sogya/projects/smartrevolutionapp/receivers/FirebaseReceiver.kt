package ru.sogya.projects.smartrevolutionapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sogya.domain.utils.Constants.NOTIFICATION_MESSAGE
import com.sogya.domain.utils.Constants.NOTIFICATION_TITLE
import ru.sogya.projects.smartrevolutionapp.workers.FirebaseWorker

class FirebaseReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val title = it.getStringExtra(NOTIFICATION_TITLE)
            val message = it.getStringExtra(NOTIFICATION_MESSAGE)

            // Create Notification Data
            val notificationData = Data.Builder()
                .putString(NOTIFICATION_TITLE, title)
                .putString(NOTIFICATION_MESSAGE, message)
                .build()

            // Init Worker
            val work = OneTimeWorkRequest.Builder(FirebaseWorker::class.java)
                .setInputData(notificationData)
                .build()

            // Start Worker
            if (context != null) {
                WorkManager.getInstance(context).beginWith(work).enqueue()
            }

            Log.d(javaClass.name, "WorkManager is Enqueued.")
        }
    }
}