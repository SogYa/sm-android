package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context
import com.sogya.data.repository.LocalDataBaseRepositoryImpl

class App : Application() {


    companion object {
        private lateinit var app: App
        private lateinit var repositoryImpl: LocalDataBaseRepositoryImpl

        fun getApplicationContext(): Context {
            repositoryImpl = LocalDataBaseRepositoryImpl(app.applicationContext)
            return app.applicationContext
        }

        fun getRoom(): LocalDataBaseRepositoryImpl {
            return repositoryImpl
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}