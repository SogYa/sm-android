package ru.sogya.projects.smartrevolutionapp.screens.firebase.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.domain.usecases.firebase.LogInUserUseCase
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class FirebaseAuthVM : ViewModel() {
    private val repository = FirebaseRepositoryImpl()
    private val logInUserUseCase = LogInUserUseCase(repository)
    private var loadingLiveData = MutableLiveData<Int>()

    fun logIn(email: String, password: String, myCallBack: MyCallBack<Boolean>) {
        logInUserUseCase.invoke(email, password, object : MyCallBack<String> {
            override fun data(t: String) {
                myCallBack.data(true)
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }

            override fun error() {
                myCallBack.error()
                loadingLiveData.value = VisibilityStates.GONE.visibility
            }
        })
    }
}