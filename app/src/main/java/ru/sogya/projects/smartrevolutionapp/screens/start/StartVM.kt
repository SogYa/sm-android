package ru.sogya.projects.smartrevolutionapp.screens.start

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.GetStatesUseCase
import com.sogya.domain.usecases.databaseusecase.states.CheckStateExistUSeCase
import com.sogya.domain.usecases.databaseusecase.states.GetStatesByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.UpdateStateUseCase
import com.sogya.domain.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class StartVM : ViewModel() {
    private val navigationLiveData = MutableLiveData<Int>()
    private val repository = App.getRoom()
    private val networkRepository = App.getNetworkRepository()
    private val getAllStatesUseCase = GetStatesUseCase(networkRepository)
    private val checkStateExistUSeCase = CheckStateExistUSeCase(repository)
    private val updateStateUseCase = UpdateStateUseCase(repository)
    private val getStateById = GetStatesByIdUseCase(repository)

    init {
        if (isFirebaseAuth()) {
            if (isAuth()) {
                if (SPControl.getInstance().getBoolPrefs(Constants.PREFS_IS_LOCKED)) {
                    navigationLiveData.value = R.id.action_startFragment_to_lockFragment
                } else {
                    val ownerId = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
                    val token = SPControl.getInstance().getStringPrefs(Constants.AUTH_TOKEN)
                    getAllStatesUseCase.invoke(ownerId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DisposableSingleObserver<List<StateDomain>>() {
                            override fun onSuccess(t: List<StateDomain>) {
                                t.forEach {
                                    if (checkStateExistUSeCase.invoke(it.entityId)) {
                                        val oldState = getStateById.invoke(it.entityId)
                                        val newState = StateDomain(
                                            it.entityId,
                                            it.state,
                                            it.lastUpdated,
                                            it.lastChanged,
                                            it.attributesDomain,
                                            oldState.ownerId,
                                            oldState.groupId
                                        )
                                        updateStateUseCase.invoke(newState)
                                    }
                                }
                            }

                            override fun onError(e: Throwable) {
                                Log.d("StatesError", e.message.toString())
                            }
                        })
                    navigationLiveData.value = R.id.action_startFragment_to_homeFragment
                }
            } else {
                navigationLiveData.value = R.id.action_startFragment_to_serversFragment
            }
        } else {
            navigationLiveData.value = R.id.action_startFragment_to_firebaseAuthFragment
        }

    }

    fun getNavLiveData() = navigationLiveData

    private fun isFirebaseAuth(): Boolean {
        return SPControl.getInstance().getBoolPrefs(Constants.IS_FIREBASE_AUTH)
    }

    private fun isAuth(): Boolean {
        return SPControl.getInstance().getStringPrefs(Constants.AUTH_TOKEN).isNotEmpty()
    }
}