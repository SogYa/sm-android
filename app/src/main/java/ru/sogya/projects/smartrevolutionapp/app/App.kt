package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.data.repository.LocalDataBaseRepositoryImpl
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.repository.NetworkStatesRepositoryImpl
import com.sogya.data.repository.SharedPreferencesRepositoryImpl
import com.sogya.data.repository.WebSocketRepositoryImpl
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.repository.NetworkRepository
import com.sogya.domain.repository.NetworkStatesRepository
import com.sogya.domain.repository.SharedPreferencesRepository
import com.sogya.domain.repository.WebSocketRepository
import com.sogya.domain.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    companion object {
        private lateinit var app: App
        private lateinit var repository: LocalDataBaseRepository
        private lateinit var firebaseRepository: FirebaseRepository
        private lateinit var webSocketRepository: WebSocketRepository
        private lateinit var networkRepository: NetworkRepository
        private lateinit var sharedPreferencesRepository: SharedPreferencesRepository
        private lateinit var networkStatesRepository: NetworkStatesRepository
        private var isSubscribed = false
        private const val SUBSCRIBE_FAILED = "Error: "

        fun getAppContext(): Context {
            return app.applicationContext
        }

        fun getRoom(): LocalDataBaseRepository {
            return repository
        }

        fun getFirebase(): FirebaseRepository {
            return firebaseRepository
        }

        fun getWebSocketRepository(): WebSocketRepository {
            return webSocketRepository
        }

        fun getNetworkRepository(): NetworkRepository {
            return networkRepository
        }

        fun getSharedPreferncesRepository(): SharedPreferencesRepository {
            return sharedPreferencesRepository
        }

        fun getNetworkStatesRepository(): NetworkStatesRepository {
            return networkStatesRepository
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
        repository = LocalDataBaseRepositoryImpl(appContext)
        firebaseRepository = FirebaseRepositoryImpl()
        webSocketRepository = WebSocketRepositoryImpl()
        networkRepository = NetworkRepositoryImpl()
        sharedPreferencesRepository = SharedPreferencesRepositoryImpl(appContext)
        networkStatesRepository = NetworkStatesRepositoryImpl(appContext)
        subscribeToFirebaseCloudMessaging()
        MapKitFactory.setApiKey("8fb09c9c-0e0e-4aaf-b5b9-f6903d7e6b8d")
    }
}