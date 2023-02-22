package ru.sogya.projects.smartrevolutionapp.screens.states.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdLiveDataUseCase
import ru.sogya.projects.smartrevolutionapp.app.App

class SensorVM : ViewModel() {
    private val repository = App.getRoom()
    private val getStateByIdUseCase = GetStateByIdLiveDataUseCase(repository)
    private var stateLiveData: LiveData<StateDomain> = MutableLiveData()

    fun getState(stateId: String) {
        stateLiveData = getStateByIdUseCase.invoke(stateId)
    }

    fun getStateLiveData(): LiveData<StateDomain> = stateLiveData
}