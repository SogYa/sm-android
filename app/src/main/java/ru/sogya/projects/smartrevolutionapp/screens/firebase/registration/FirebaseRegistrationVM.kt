package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.CreateUserUseCase
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.usecases.firebase.WriteUserUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseRegistrationVM : ViewModel() {
    private val repository = App.getFirebase()
    private val createUserUseCase = CreateUserUseCase(repository)
    private val sendEmailVerificationUseCase = SendEmailVerivicationUseCase(App.getFirebase())
    private val writeUserUseCase = WriteUserUseCase(repository)
    private val errorLiveData = MutableLiveData<String>()
    private var loadingLiveData = MutableLiveData<Int>()

    fun signUpUser(name: String, email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        createUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                writeUserUseCase.invoke(name, email, object : MyCallBack<String> {
                    override fun error(string: String) {
                        errorLiveData.value = string
                    }
                })
                sendEmailVerificationUseCase.invoke(object : MyCallBack<String> {
                    override fun data(t: String) {
                        SPControl.getInstance().updatePrefs(Constants.IS_FIREBASE_AUTH, true)
                        myCallBack.data(true)
                    }

                    override fun error(string: String) {
                        errorLiveData.value = string
                        Log.d("Firebase", "error")
                    }

                })
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error(string: String) {
                myCallBack.error(string)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }

    fun getLoadingLiveData() = loadingLiveData

    fun getErrorLiveData() = errorLiveData
}