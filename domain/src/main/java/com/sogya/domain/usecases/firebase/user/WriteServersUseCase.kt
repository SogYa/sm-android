package com.sogya.domain.usecases.firebase.user

import com.sogya.domain.models.ServerDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class WriteServersUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(serverList: List<ServerDomain>,myCallBack: MyCallBack<String>) = repository.writeServerUserLists(serverList,myCallBack)
}