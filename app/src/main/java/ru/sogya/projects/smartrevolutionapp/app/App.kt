package ru.sogya.projects.smartrevolutionapp.app

import android.app.Application
import android.content.Context

class App : Application() {


    companion object {
        private lateinit var app: App

        fun getIstance(): App {
            return App()
        }

        fun getApplicationContext(): Context {
            return app.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}