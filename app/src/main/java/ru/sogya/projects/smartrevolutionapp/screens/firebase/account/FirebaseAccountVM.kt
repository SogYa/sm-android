package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.usecases.firebase.LogOutUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class FirebaseAccountVM : ViewModel() {
    private val logOutUseCase = LogOutUseCase(App.getFirebase())
    fun logOut(myCallBack: MyCallBack<Boolean>) {
        viewModelScope.launch {
            val job = launch {
                logOutUseCase.invoke()
            }
            job.join()
            SPControl.getInstance().updatePrefs(Constants.IS_FIREBASE_AUTH,false)
            myCallBack.data(true)
        }
    }
}