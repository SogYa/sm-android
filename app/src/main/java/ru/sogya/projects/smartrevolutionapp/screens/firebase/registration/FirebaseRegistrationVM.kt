package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.domain.usecases.firebase.CreateUserUseCase
import com.sogya.domain.usecases.firebase.SendEmailVerivicationUseCase
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseRegistrationVM : ViewModel() {
    private val repository = FirebaseRepositoryImpl()
    private val createUserUseCase = CreateUserUseCase(repository)
    private val sendEmailVerivicationUseCase = SendEmailVerivicationUseCase(repository)
    private var loadingLiveData = MutableLiveData<Int>()

    fun signUpUser(email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        createUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                sendEmailVerivicationUseCase.invoke(object : MyCallBack<Boolean>{
                    override fun data(t: Boolean) {
                        myCallBack.data(true)
                    }

                    override fun error() {
                        myCallBack.data(false)
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