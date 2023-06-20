package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.UserDomain
import com.sogya.domain.usecases.firebase.user.LogOutUseCase
import com.sogya.domain.usecases.firebase.user.ReadUserUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FirebaseAccountVM @Inject constructor(
    private val updatePrefsUseCase: UpdatePrefsUseCase,
    readUserUseCase: ReadUserUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {
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

    fun getUserLiveData(): LiveData<UserDomain?> = userLiveData

    fun getErrorLiveData(): LiveData<String> = errorLiveData
}