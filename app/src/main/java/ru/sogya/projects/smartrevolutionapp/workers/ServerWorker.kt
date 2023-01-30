package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.sogya.projects.smartrevolutionapp.app.App

class ServerWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val repository = App.getWebSocketRepository()
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}