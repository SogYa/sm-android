package ru.sogya.projects.smartrevolutionapp.screens.firebase.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.user.LogInUserUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthVM @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val updatePrefsUseCase: UpdatePrefsUseCase,
) : ViewModel() {
    private var loadingLiveData = MutableLiveData<Int>()

    fun logIn(email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        logInUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                myCallBack.data(true)
                updatePrefsUseCase.invoke(Constants.IS_FIREBASE_AUTH, true)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error() {
                myCallBack.error()
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error(error: String) {
                super.error(error)
                myCallBack.error(error)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }

    fun getLoadingLiveData(): LiveData<Int> = loadingLiveData
}