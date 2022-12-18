package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.utils.Constants
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.GetStatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class StateAddingVM : ViewModel() {

    private val repository = NetworkRepositoryImpl()
    private val getStatesUseCase = GetStatesUseCase(repository)
    val statesLiveData = MutableLiveData<List<StateDomain>>()
    val loadingViewLiveData = MutableLiveData<Int>()


    init {
        val url = SPControl.getInstance().getStringPrefs(Constants.URI)
        val token = SPControl.getInstance().getStringPrefs(Constants.AUTH_TOKEN)
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
}
