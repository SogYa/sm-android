package ru.sogya.projects.smartrevolutionapp.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.GetAllStatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App

class DashboardVM : ViewModel() {
    val loadingViewLiveData = MutableLiveData<Int>()
    private var itemsLiveData: LiveData<List<StateDomain>> = MutableLiveData()
    private val getStatesUseCase = GetAllStatesUseCase(App.getRoom())
    private val deleteUseCase = DeleteStateUseCase(App.getRoom())

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