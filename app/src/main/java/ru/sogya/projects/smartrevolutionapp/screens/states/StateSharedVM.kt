package ru.sogya.projects.smartrevolutionapp.screens.states

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.models.requests.CallService
import com.sogya.data.models.requests.CallServiceTarget
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdLiveDataUseCase
import com.sogya.domain.usecases.websocketus.SendMessageUseCase
import ru.sogya.projects.smartrevolutionapp.app.App

class StateSharedVM : ViewModel() {
    private val repository = App.getRoom()
    private val getStateByIdUseCase = GetStateByIdLiveDataUseCase(repository)
    private var stateLiveData: LiveData<StateDomain> = MutableLiveData()
    private val webSocketRepository = App.getWebSocketRepository()
    private val sendMessageUseCase = SendMessageUseCase(webSocketRepository)

    companion object {
        var ID_SERVICE_COUNT = 25
    }

    fun getState(stateId: String) {
        stateLiveData = getStateByIdUseCase.invoke(stateId)
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