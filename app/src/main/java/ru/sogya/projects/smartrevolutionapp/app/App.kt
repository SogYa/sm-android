package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.data.repository.LocalDataBaseRepositoryImpl
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.repository.LocalDataBaseRepository

class App : Application() {


    companion object {
        private lateinit var app: App
        private lateinit var repository: LocalDataBaseRepository
        private lateinit var firebaseRepository: FirebaseRepository

        fun getApplicationContext(): Context {
            return app.applicationContext
        }

        fun getRoom(): LocalDataBaseRepository {
            return repository
        }

        fun getFirebase(): FirebaseRepository {
            return firebaseRepository
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        FirebaseApp.initializeApp(app.applicationContext)
        repository = LocalDataBaseRepositoryImpl(app.applicationContext)
        firebaseRepository = FirebaseRepositoryImpl()

    }
}