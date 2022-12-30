package ru.sogya.projects.smartrevolutionapp.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.repository.LocalDataBaseRepositoryImpl
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.DeleteStateUseCase
import com.sogya.domain.usecases.databaseusecase.GetAllStatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class DashboardVM : ViewModel() {
    val loadingViewLiveData = MutableLiveData<Int>()
    val itemsLiveData = MutableLiveData<List<StateDomain>>()
    private val repository = LocalDataBaseRepositoryImpl(App.getApplicationContext())
    private val getStatesUseCase = GetAllStatesUseCase(repository)
    private val deleteUseCase = DeleteStateUseCase(repository)

    init {
        getItemsFromLocalDB()
    }

    fun getItemsFromLocalDB() {
        getStatesUseCase.invoke().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<List<StateDomain>>() {
                override fun onNext(t: List<StateDomain>) {
                    itemsLiveData.postValue(t)
                    loadingViewLiveData.postValue(VisibilityStates.GONE.visibility)
                }

                override fun onError(e: Throwable) {
                    Log.d("DataBaseGetAllError", e.message.toString())
                }

                override fun onComplete() {
                    Log.d("DataBaseObserver", "Completed")
                }

            })
    }

    fun deleteState(stateId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUseCase.invoke(stateId)
        }
    }
}