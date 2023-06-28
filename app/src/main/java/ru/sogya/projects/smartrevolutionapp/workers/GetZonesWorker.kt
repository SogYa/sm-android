package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

@HiltWorker
class GetZonesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    localDataBaseRepository: LocalDataBaseRepository,
    sharedPreferencesRepository: SharedPreferencesRepository,
    networkRepository: NetworkRepository
) :
    CoroutineWorker(context, workerParams) {
    private lateinit var result: Result
    private val getStatesUseCase = GetStatesUseCase(networkRepository)
    private val insertZoneUseCase = InsertZoneUseCase(localDataBaseRepository)
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    override suspend fun doWork(): Result {
        val baseUrl = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
        coroutineScope {
            getStatesUseCase.invoke(baseUrl, token).flowOn(Dispatchers.IO)
                .catch {
                    Log.d("StatesError", it.message.toString())
                    result = Result.failure()
                }
                .collect { stateDomains ->
                    stateDomains.forEach {
                        if (it.isZone()) {
                            val zoneDomain = ZoneDomain(
                                it.entityId,
                                baseUrl,
                                it.attributes?.friendlyName,
                                it.attributes?.latitude?.toDouble(),
                                it.attributes?.longitude?.toDouble()
                            )

                            insertZoneUseCase.invoke(zoneDomain)
                        }
                        result = Result.success()
                    }
                }
        }
        return result
    }
}