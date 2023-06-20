package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.usecases.firebase.user.CreateUserUseCase
import com.sogya.domain.usecases.firebase.user.WriteUserUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import javax.inject.Inject
@HiltViewModel
class FirebaseRegistrationVM @Inject constructor(
    private val updatePrefsUseCase: UpdatePrefsUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerivicationUseCase,
    private val writeUserUseCase: WriteUserUseCase,
) : ViewModel() {

    private val errorLiveData = MutableLiveData<String>()
    private var loadingLiveData = MutableLiveData<Int>()

    fun signUpUser(name: String, email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        createUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                writeUserUseCase.invoke(name, email, object : MyCallBack<String> {
                    override fun error(error: String) {
                        errorLiveData.value = error
                    }
                })
                sendEmailVerificationUseCase.invoke(object : MyCallBack<String> {
                    override fun data(t: String) {
                        updatePrefsUseCase.invoke(Constants.IS_FIREBASE_AUTH, true)
                        myCallBack.data(true)
                    }

                    override fun error(error: String) {
                        errorLiveData.value = error
                        Log.d("Firebase", "error")
                    }

                })
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error(error: String) {
                myCallBack.error(error)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }

    fun getLoadingLiveData(): LiveData<Int> = loadingLiveData

    fun getErrorLiveData(): LiveData<String> = errorLiveData
}