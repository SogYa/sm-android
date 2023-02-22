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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class StateAddingVM : ViewModel() {

    private val repository = App.getNetworkRepository()
    private val getStatesUseCase = GetStatesUseCase(repository)
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val insertStateUseCase = InsertStateUseCase(App.getRoom())
    private val statesLiveData = MutableLiveData<List<StateDomain>>()
    private val loadingViewLiveData = MutableLiveData<Int>()


    init {
        val url = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
        getStatesUseCase.invoke(url, token).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<StateDomain>>() {
                override fun onSuccess(t: List<StateDomain>) {
                    statesLiveData.postValue(t)
                    loadingViewLiveData.postValue(VisibilityStates.GONE.visibility)
                }

                override fun onError(e: Throwable) {
                    Log.d("StatesError", e.message.toString())
                }

            })
    }

    fun addStatesToDataBase(
        states: HashSet<StateDomain>,
        groupId: Int,
        myCallBack: MyCallBack<Boolean>
    ) {
        val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val listOfStates = states.toList()
        viewModelScope.launch(Dispatchers.IO) {
            val job = launch {
                listOfStates.forEach {
                    it.ownerId = ownerId
                    it.groupId = groupId
                }
            }
            job.join()
            insertStateUseCase.invoke(listOfStates)
        }
        myCallBack.data(true)
    }

    fun getStatesLiveData(): LiveData<List<StateDomain>> = statesLiveData
    fun getLoadingLiveData(): LiveData<Int> = loadingViewLiveData
}
