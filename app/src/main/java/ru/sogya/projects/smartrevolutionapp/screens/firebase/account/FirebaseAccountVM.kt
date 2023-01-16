package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.domain.usecases.firebase.LogOutUseCase
import com.sogya.domain.utils.MyCallBack
import kotlinx.coroutines.launch

class FirebaseAccountVM : ViewModel() {
    private val repository = FirebaseRepositoryImpl()
    private val logOutUseCase = LogOutUseCase(repository)
    fun logOut(myCallBack: MyCallBack<Boolean>) {
        viewModelScope.launch {
            val job = launch {
                logOutUseCase.invoke()
            }
            job.join()
            myCallBack.data(true)
        }
    }
}