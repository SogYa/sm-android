package ru.sogya.projects.smartrevolutionapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.usecases.websocketus.CloseUseCase
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.app.App

class MainVM : ViewModel() {
    private val serverNameLiveData = MutableLiveData<String>()
    private val serverUriLiveData = MutableLiveData<String>()
    private val repository = App.getSharedPreferncesRepository()
    private val webSocketRepository = App.getWebSocketRepository()
    private val closeWebSocketUseCase = CloseUseCase(webSocketRepository)
    private val getStringPrefsUseCase = GetStringPrefsUseCase(repository)
    private val updatePrefsUseCase = UpdatePrefsUseCase(repository)

    fun logOut() {
        updatePrefsUseCase.invoke(Constants.AUTH_TOKEN, "")
        updatePrefsUseCase.invoke(Constants.SERVER_URI, "")
        updatePrefsUseCase.invoke(Constants.TEST_MODE, false)
        updatePrefsUseCase.invoke(Constants.SERVER_NAME, "")
        updatePrefsUseCase.invoke(Constants.PREFS_APPLOCK_PINCODE, "")
        closeWebSocketUseCase.invoke()
    }

    fun getServerState() {
        serverUriLiveData.value =
            getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        serverNameLiveData.value =
            getStringPrefsUseCase.invoke(Constants.SERVER_NAME)
    }

    fun getServerNameLiveData(): LiveData<String> = serverNameLiveData

    fun getServerUriLiveData(): LiveData<String> = serverUriLiveData
}