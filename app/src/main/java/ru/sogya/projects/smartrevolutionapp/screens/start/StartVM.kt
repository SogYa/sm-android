package ru.sogya.projects.smartrevolutionapp.screens.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.databaseusecase.states.CheckStateExistUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.UpdateStateUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetBooleanPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.sogya.projects.smartrevolutionapp.R
import javax.inject.Inject

@HiltViewModel
class StartVM @Inject constructor(
    private val getBooleanPrefsUseCase: GetBooleanPrefsUseCase,
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
    getAllStatesUseCase: GetStatesUseCase,
    private val checkStateExistUseCase: CheckStateExistUseCase,
    private val updateStateUseCase: UpdateStateUseCase,
    private val getStateById: GetStateByIdUseCase,
) : ViewModel() {
    private val navigationLiveData = MutableLiveData<Int>()

    init {
        if (isFirebaseAuth()) {
            if (isAuth()) {
                if (getBooleanPrefsUseCase.invoke(Constants.PREFS_IS_LOCKED)) {
                    navigationLiveData.value = R.id.action_startFragment_to_lockFragment
                } else {
                    val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
                    val token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
                    getAllStatesUseCase.invoke(ownerId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : DisposableSingleObserver<List<StateDomain>>() {
                            override fun onSuccess(t: List<StateDomain>) {
                                t.forEach {
                                    if (checkStateExistUseCase.invoke(it.entityId)) {
                                        val oldState = getStateById.invoke(it.entityId)
                                        val newState = StateDomain(
                                            it.entityId,
                                            it.state,
                                            it.lastUpdated,
                                            it.lastChanged,
                                            it.attributes,
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

    fun getNavLiveData(): LiveData<Int> = navigationLiveData

    private fun isFirebaseAuth(): Boolean {
        return getBooleanPrefsUseCase.invoke(Constants.IS_FIREBASE_AUTH)
    }

    private fun isAuth(): Boolean {
        return getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN).isNotEmpty()
    }
}