package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.utils.Constants
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllStatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class DashboardVM : ViewModel() {
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getStatesUseCase = GetAllStatesUseCase(repository)
    private val deleteUseCase = DeleteStateUseCase(repository)

    init {
        val serverUri = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
        itemsLiveData = getStatesUseCase.invoke(serverUri)
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
    }

    fun getItemsLiveDat() = itemsLiveData
}