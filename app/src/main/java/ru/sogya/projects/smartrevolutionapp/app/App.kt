package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import com.sogya.data.repository.LocalDataBaseRepositoryImpl
import com.sogya.domain.repository.LocalDataBaseRepository

class App : Application() {


    companion object {
        private lateinit var app: App
        private lateinit var repository: LocalDataBaseRepository

        fun getApplicationContext(): Context {
            return app.applicationContext
        }

        fun getRoom(): LocalDataBaseRepository {
            return repository
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        repository = LocalDataBaseRepositoryImpl(app.applicationContext)
    }
}