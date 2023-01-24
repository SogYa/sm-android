package com.sogya.domain.usecases.firebase

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class SendEmailVerivicationUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(myCallBack: MyCallBack<String>) = repository.sendEmailVerification(myCallBack)
}