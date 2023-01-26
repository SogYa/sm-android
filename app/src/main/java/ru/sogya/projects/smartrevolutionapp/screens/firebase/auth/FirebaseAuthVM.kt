package ru.sogya.projects.smartrevolutionapp.screens.firebase.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.user.LogInUserUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseAuthVM : ViewModel() {
    private val logInUserUseCase = LogInUserUseCase(App.getFirebase())
    private var loadingLiveData = MutableLiveData<Int>()

    fun logIn(email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        logInUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                myCallBack.data(true)
                SPControl.getInstance().updatePrefs(Constants.IS_FIREBASE_AUTH,true)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error() {
                myCallBack.error()
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }
    fun getLoadingLiveData() = loadingLiveData
}