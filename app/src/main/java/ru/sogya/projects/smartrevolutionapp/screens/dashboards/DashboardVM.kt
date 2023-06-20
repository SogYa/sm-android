package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.models.requests.CallService
import com.sogya.data.models.requests.CallServiceData
import com.sogya.data.models.requests.CallServiceTarget
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllByGroupIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.websockets.SendMessageUseCase
import com.sogya.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getAllByGroup: GetAllByGroupIdUseCase,
    private val getAllStates: GetAllStatesUseCase,
    private val deleteUseCase: DeleteStateUseCase,
) : ViewModel() {
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()

    companion object {
        private var ID_SERVICE_COUNT = 24
    }

    fun getGroupStates(groupId: Int) {
        itemsLiveData =
            if (groupId == Constants.DEFAULT_GROUP_ID) {
                val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
                getAllStates(ownerId)
            } else {
                getAllByGroup(groupId)
            }
    }

    fun getItemsLiveDat()
            : LiveData<List<StateDomain>> = itemsLiveData

    fun callSwitchService(stateId: String, switchState: String) {
        val serviceDomain = stateId.substringBefore(".")
        val targetDevice = CallServiceTarget(stateId)
        val service =
            CallService(
                id = ID_SERVICE_COUNT,
                domain = serviceDomain,
                service = switchState,
                target = targetDevice
            )
        println(service)
        sendMessageUseCase.invoke(service)
        ID_SERVICE_COUNT++
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
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

    fun changeCoverValue(stateId: String, value: Int) {
        val serviceDomain = stateId.substringBefore(".")
        val targetDevice = CallServiceTarget(stateId)
        val serviceData = CallServiceData(value)
        val service =
            CallService(
                id = ID_SERVICE_COUNT,
                domain = serviceDomain,
                service = "set_cover_position",
                target = targetDevice,
                serviceData = serviceData
            )
        println(service)
        sendMessageUseCase.invoke(service)
        ID_SERVICE_COUNT++
    }
}
