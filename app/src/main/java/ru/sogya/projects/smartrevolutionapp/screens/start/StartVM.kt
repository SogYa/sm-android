package ru.sogya.projects.smartrevolutionapp.screens.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.models.AttributesData
import com.sogya.data.models.StateData
import com.sogya.domain.usecases.databaseusecase.states.CheckStateExistUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.UpdateStateUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.sharedpreferences.GetBooleanPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.R
import javax.inject.Inject

@HiltViewModel
class StartVM @Inject constructor(
    private val getBooleanPrefsUseCase: GetBooleanPrefsUseCase,
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
    private val getAllStatesUseCase: GetStatesUseCase,
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
                    updateStatesAfterRestart(token, ownerId)
                }
            } else {
                navigationLiveData.value = R.id.action_startFragment_to_serversFragment
            }
        } else {
            navigationLiveData.value = R.id.action_startFragment_to_firebaseAuthFragment
        }

    }

    private fun updateStatesAfterRestart(token: String, ownerId: String) {
        viewModelScope.launch {
            getAllStatesUseCase.invoke(ownerId, token)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d("StatesError", it.message.toString())
                }.collect {
                    it.forEach {
                        if (checkStateExistUseCase.invoke(it.entityId)) {
                            val oldState = getStateById.invoke(it.entityId)
                            val newState = StateData(
                                it.entityId,
                                it.state,
                                it.lastUpdated,
                                it.lastChanged,
                                it.attributes as AttributesData?,
                                oldState.ownerId,
                                oldState.groupId
                            )
                            updateStateUseCase.invoke(newState)
                        }
                    }
                    navigationLiveData.value = R.id.action_startFragment_to_homeFragment
                }
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