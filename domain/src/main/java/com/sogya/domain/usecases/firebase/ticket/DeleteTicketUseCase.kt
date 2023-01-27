package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class DeleteTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(
        ticketId: String,
        myCallBack: MyCallBack<String>
    ) = repository.deleteTicketById(ticketId, myCallBack)
}