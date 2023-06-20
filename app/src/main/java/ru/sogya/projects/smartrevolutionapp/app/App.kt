package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.sogya.domain.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    companion object {
        private lateinit var app: App
        private var isSubscribed = false
        private const val SUBSCRIBE_FAILED = "Error: "

        fun getAppContext(): Context {
            return app.applicationContext
        }

        private fun subscribeToFirebaseCloudMessaging() {
            if (!isSubscribed) {
                FirebaseAuth.getInstance().addAuthStateListener {
                    if (it.currentUser != null) {
                        FirebaseMessaging.getInstance()
                            .subscribeToTopic(Constants.CLOUD_MESSAGING_INFO_TOPIC)
                            .addOnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Toast.makeText(
                                        app.applicationContext,
                                        SUBSCRIBE_FAILED + task.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
                isSubscribed = true
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        val appContext = app.applicationContext
        FirebaseApp.initializeApp(appContext)
        subscribeToFirebaseCloudMessaging()
        MapKitFactory.setApiKey("8fb09c9c-0e0e-4aaf-b5b9-f6903d7e6b8d")
    }
}