package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.firebase.CreateUserUseCase
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseRegistrationVM : ViewModel() {
    private val createUserUseCase = CreateUserUseCase(App.getFirebase())
    private val sendEmailVerivicationUseCase = SendEmailVerivicationUseCase(App.getFirebase())
    private var loadingLiveData = MutableLiveData<Int>()

    fun signUpUser(email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        createUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                sendEmailVerivicationUseCase.invoke(object : MyCallBack<Boolean> {
                    override fun data(t: Boolean) {
                        SPControl.getInstance().updatePrefs(Constants.IS_FIREBASE_AUTH, true)
                        myCallBack.data(true)
                    }

                    override fun error() {
                        myCallBack.data(false)
                        Log.d("Firebase","error")
                    }

                })
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error() {
                myCallBack.error()
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }
}