package ru.sogya.projects.smartrevolutionapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.network.GetNetworkStateUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.usecases.websockets.CloseUseCase
import com.sogya.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val closeWebSocketUseCase: CloseUseCase,
    private val updatePrefsUseCase: UpdatePrefsUseCase,
    getNetworkStatesUseCase: GetNetworkStateUseCase
) : ViewModel() {
    private val networkStateLiveData: LiveData<Boolean> = getNetworkStatesUseCase()

    fun getNetworkStateLiveData() = networkStateLiveData

    fun logOut() {
        updatePrefsUseCase.invoke(Constants.AUTH_TOKEN, "")
        updatePrefsUseCase.invoke(Constants.SERVER_URI, "")
        updatePrefsUseCase.invoke(Constants.TEST_MODE, false)
        updatePrefsUseCase.invoke(Constants.SERVER_NAME, "")
        updatePrefsUseCase.invoke(Constants.PREFS_APPLOCK_PINCODE, "")
        closeWebSocketUseCase.invoke()
    }
}