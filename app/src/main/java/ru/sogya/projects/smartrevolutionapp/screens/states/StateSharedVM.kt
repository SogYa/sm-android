package ru.sogya.projects.smartrevolutionapp.screens.states


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.models.requests.CallService
import com.sogya.data.models.requests.CallServiceTarget
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdLiveDataUseCase
import com.sogya.domain.usecases.network.GetStateHistoryUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.websockets.SendMessageUseCase
import com.sogya.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateSharedVM @Inject constructor(
    private val getStateByIdUseCase: GetStateByIdLiveDataUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getStateHistoryUseCase: GetStateHistoryUseCase,
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
) : ViewModel() {
    private var stateLiveData: LiveData<StateDomain> = MutableLiveData()
    private val stateHistoryLiveData = MutableLiveData<List<Float>>()

    companion object {
        var ID_SERVICE_COUNT = 25
    }

    fun getState(stateId: String) {
        stateLiveData = getStateByIdUseCase.invoke(stateId)
    }

    fun getStateHisotry() {
        val baseUri = getStringPrefsUseCase(Constants.SERVER_URI)
    }

    fun callMediaPLayerService(stateId: String, command: String) {
        val serviceDomain = stateId.substringBefore(".")
        val targetDevice = CallServiceTarget(stateId)
        val service =
            CallService(
                id = ID_SERVICE_COUNT,
                domain = serviceDomain,
                service = command,
                target = targetDevice
            )
        println(service)
        sendMessageUseCase.invoke(service)
        ID_SERVICE_COUNT++
    }

    fun getStateLiveData(): LiveData<StateDomain> = stateLiveData
}