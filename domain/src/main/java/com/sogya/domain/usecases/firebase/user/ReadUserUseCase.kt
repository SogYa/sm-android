package com.sogya.domain.usecases.firebase.user

import com.sogya.domain.models.UserDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class ReadUserUseCase(
    private val repositry: FirebaseRepository
) {
    fun invoke(myCallBack: MyCallBack<UserDomain?>) = repositry.readUser(myCallBack)
}