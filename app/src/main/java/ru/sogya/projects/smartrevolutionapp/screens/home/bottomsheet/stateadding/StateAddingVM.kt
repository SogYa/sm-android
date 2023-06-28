package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.InsertStateUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import javax.inject.Inject

@HiltViewModel
class StateAddingVM @Inject constructor(
    getStatesUseCase: GetStatesUseCase,
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
    private val insertStateUseCase: InsertStateUseCase,
) : ViewModel() {
    private val statesLiveData = MutableLiveData<List<StateDomain>>()
    private val loadingViewLiveData = MutableLiveData<Int>()


    init {
        val url = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
        viewModelScope.launch {
            getStatesUseCase.invoke(url, token).flowOn(Dispatchers.IO)
                .catch {
                    Log.d("StatesError", it.message.toString())
                }.collect {
                    statesLiveData.postValue(it)
                    loadingViewLiveData.postValue(VisibilityStates.GONE.visibility)
                }
        }
    }

    fun addStatesToDataBase(
        states: HashSet<StateDomain>,
        groupId: Int,
        myCallBack: MyCallBack<Boolean>
    ) {
        val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val listOfStates: MutableList<StateDomain> = states.toMutableList()
        Log.d("SetVM", listOfStates.toString())
        if (listOfStates.isEmpty()) {
            myCallBack.error()
        } else {
            listOfStates.forEach {
                it.ownerId = ownerId
                it.groupId = groupId
            }
            GlobalScope.async(Dispatchers.IO) {
                Log.d("Start", "Start")
                insertStateUseCase.invoke(listOfStates)
            }
        }
    }

    fun getStatesLiveData(): LiveData<List<StateDomain>> = statesLiveData
    fun getLoadingLiveData(): LiveData<Int> = loadingViewLiveData
}
