package com.sogya.domain.usecases.firebase.user

import com.sogya.domain.models.ServerFirebaseDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class WriteServersUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(serverList: List<ServerFirebaseDomain>, myCallBack: MyCallBack<String>) = repository.writeServerUserLists(serverList,myCallBack)
}