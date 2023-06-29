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
    private val getNetworkStatesUseCase: GetNetworkStateUseCase
) : ViewModel() {
    fun getNetworkStateLiveData(): LiveData<Boolean> {
        return getNetworkStatesUseCase()
    }

    fun logOut() {
        updatePrefsUseCase(Constants.AUTH_TOKEN, "")
        updatePrefsUseCase(Constants.SERVER_URI, "")
        updatePrefsUseCase(Constants.TEST_MODE, false)
        updatePrefsUseCase(Constants.SERVER_NAME, "")
        updatePrefsUseCase(Constants.PREFS_APPLOCK_PINCODE, "")
        closeWebSocketUseCase()
    }
}