package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.user.CreateUserUseCase
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.usecases.firebase.user.WriteUserUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseRegistrationVM : ViewModel() {
    private val repository = App.getFirebase()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val updatePrefsUseCase = UpdatePrefsUseCase(sharedPreferencesRepository)
    private val createUserUseCase = CreateUserUseCase(repository)
    private val sendEmailVerificationUseCase = SendEmailVerivicationUseCase(App.getFirebase())
    private val writeUserUseCase = WriteUserUseCase(repository)
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