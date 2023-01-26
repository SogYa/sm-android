package com.sogya.domain.usecases.firebase.user

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class CreateUserUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(email: String, password: String, myCallBack: MyCallBack<String>) = repository.createUser(email, password,myCallBack)
}