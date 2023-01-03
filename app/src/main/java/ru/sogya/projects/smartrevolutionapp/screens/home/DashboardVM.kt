package ru.sogya.projects.smartrevolutionapp.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.servers.GetServerByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetAllStatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App

class DashboardVM : ViewModel() {
    val loadingViewLiveData = MutableLiveData<Int>()
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getStatesUseCase = GetAllStatesUseCase(repository)
    private val deleteUseCase = DeleteStateUseCase(repository)
    private val getServerByIdUseCase = GetServerByIdUseCase(repository)

    init {
        itemsLiveData = getStatesUseCase.invoke()
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
    }

    fun getItemsLiveDat() = itemsLiveData
}