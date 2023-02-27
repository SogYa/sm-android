package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.usecases.databaseusecase.zones.InsertZoneUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.app.App

class GetZonesWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val networkRepository = App.getNetworkRepository()
    private val getStatesUseCase = GetStatesUseCase(networkRepository)
    private val roomRepository = App.getRoom()
    private val insertZoneUseCase = InsertZoneUseCase(roomRepository)
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private lateinit var result: Result
    override fun doWork(): Result {
        val baseUrl = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
        getStatesUseCase.invoke(baseUrl, token).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<StateDomain>>() {
                override fun onSuccess(t: List<StateDomain>) {
                    t.forEach {
                        if (it.isZone()) {
                            val zoneDomain = ZoneDomain(
                                it.entityId,
                                baseUrl,
                                it.attributesDomain?.friendlyName,
                                it.attributesDomain?.latitude?.toDouble(),
                                it.attributesDomain?.longitude?.toDouble()
                            )

                            insertZoneUseCase.invoke(zoneDomain)
                        }
                        result = Result.success()
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d("StatesError", e.message.toString())
                    result = Result.failure()
                }
            })
        return result
    }
}