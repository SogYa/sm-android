package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.UserDomain
import com.sogya.domain.usecases.firebase.user.LogOutUseCase
import com.sogya.domain.usecases.firebase.user.ReadUserUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class FirebaseAccountVM : ViewModel() {
    private val readUserUseCase = ReadUserUseCase(App.getFirebase())
    private val logOutUseCase = LogOutUseCase(App.getFirebase())
    private val userLiveData: MutableLiveData<UserDomain?> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        readUserUseCase.invoke(object : MyCallBack<UserDomain?> {
            override fun data(t: UserDomain?) {
                userLiveData.value = t
            }

            override fun error(error: String) {
                errorLiveData.value = error
            }
        })
    }

    fun logOut(myCallBack: MyCallBack<Boolean>) {
        viewModelScope.launch {
            val job = launch {
                logOutUseCase.invoke()
            }
            job.join()
            SPControl.getInstance().updatePrefs(Constants.IS_FIREBASE_AUTH, false)
            myCallBack.data(true)
        }
    }

    fun getUserLiveData() = userLiveData

    fun getErrorLiveData() = errorLiveData
}