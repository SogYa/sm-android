package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.UserDomain
import com.sogya.domain.usecases.firebase.user.LogOutUseCase
import com.sogya.domain.usecases.firebase.user.ReadUserUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App

class FirebaseAccountVM : ViewModel() {
    private val firebaseRepository = App.getFirebase()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val updatePrefsUseCase = UpdatePrefsUseCase(sharedPreferencesRepository)
    private val readUserUseCase = ReadUserUseCase(firebaseRepository)
    private val logOutUseCase = LogOutUseCase(firebaseRepository)
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
            updatePrefsUseCase.invoke(Constants.IS_FIREBASE_AUTH, false)
            myCallBack.data(true)
        }
    }

    fun getUserLiveData() = userLiveData

    fun getErrorLiveData() = errorLiveData
}