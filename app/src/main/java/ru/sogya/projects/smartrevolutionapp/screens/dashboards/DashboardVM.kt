package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.utils.Constants
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllByGroupIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllStatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class DashboardVM : ViewModel() {
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getAllByGroup = GetAllByGroupIdUseCase(repository)
    private val getAllStates = GetAllStatesUseCase(repository)
    private val deleteUseCase = DeleteStateUseCase(repository)

    fun getGroupStates(groupId: Int) {
        itemsLiveData = if (groupId == Constants.DEFAULT_GROUP_ID) {
            val ownerId = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
            getAllStates.invoke(ownerId)
        } else {
            getAllByGroup.invoke(groupId)
        }
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
    }

    fun getItemsLiveDat() = itemsLiveData
}