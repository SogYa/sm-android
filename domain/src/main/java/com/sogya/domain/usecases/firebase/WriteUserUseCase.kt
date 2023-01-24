package com.sogya.domain.usecases.firebase

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class WriteUserUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(name: String, email: String, myCallBack: MyCallBack<String>) =
        repository.writeUser(name, email, myCallBack)
}