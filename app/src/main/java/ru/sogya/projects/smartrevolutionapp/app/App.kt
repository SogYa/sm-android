package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.sogya.data.repository.*
import com.sogya.domain.repository.*
import com.yandex.mapkit.MapKitFactory

class App : Application() {


    companion object {
        private lateinit var app: App
        private lateinit var repository: LocalDataBaseRepository
        private lateinit var firebaseRepository: FirebaseRepository
        private lateinit var webSocketRepository: WebSocketRepository
        private lateinit var networkRepository: NetworkRepository
        private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

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
        MapKitFactory.setApiKey("8fb09c9c-0e0e-4aaf-b5b9-f6903d7e6b8d")

    }
}