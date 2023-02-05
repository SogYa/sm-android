package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.models.requests.CallService
import com.sogya.data.models.requests.CallServiceTarget
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllByGroupIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.websocketus.SendMessageUseCase
import com.sogya.domain.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App

class DashboardVM : ViewModel() {
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val webSocketRepository = App.getWebSocketRepository()
    private val sendMessageUseCase = SendMessageUseCase(webSocketRepository)
    private val getAllByGroup = GetAllByGroupIdUseCase(repository)
    private val getAllStates = GetAllStatesUseCase(repository)
    private val deleteUseCase = DeleteStateUseCase(repository)
    companion object{
        private var ID_SERVICE_COUNT = 24
    }

    fun getGroupStates(groupId: Int) {
        itemsLiveData = if (groupId == Constants.DEFAULT_GROUP_ID) {
            val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
            getAllStates.invoke(ownerId)
        } else {
            getAllByGroup.invoke(groupId)
        }
    }

    fun callSwitchService(stateId: String, switchState: String) {
        val serviceDomain = stateId.substringBefore(".")
        val targetDevice = CallServiceTarget(stateId)
        val service =
            CallService(id = ID_SERVICE_COUNT, domain = serviceDomain, service = switchState, target = targetDevice)
        println(service)
        sendMessageUseCase.invoke(service)
        ID_SERVICE_COUNT++
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
    }

    fun getItemsLiveDat() = itemsLiveData
}