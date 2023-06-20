package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.repository.NetworkRepository
import com.sogya.domain.repository.SharedPreferencesRepository
import com.sogya.domain.usecases.databaseusecase.zones.InsertZoneUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

@HiltWorker
class GetZonesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    localDataBaseRepository: LocalDataBaseRepository,
    sharedPreferencesRepository: SharedPreferencesRepository,
    networkRepository: NetworkRepository
) :
    Worker(context, workerParams) {
    private lateinit var result: Result
    private val getStatesUseCase = GetStatesUseCase(networkRepository)
    private val insertZoneUseCase = InsertZoneUseCase(localDataBaseRepository)
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
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