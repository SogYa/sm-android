package com.sogya.domain.usecases.firebase.user

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class LogInUserUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(email: String, password: String,myCallBack: MyCallBack<String>) = repository.logInUser(email,password,myCallBack)
}