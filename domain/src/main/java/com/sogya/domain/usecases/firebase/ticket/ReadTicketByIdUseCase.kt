package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.models.TicketDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class ReadTicketByIdUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(ticketId: String, myCallBack: MyCallBack<TicketDomain>) =
        repository.readTicketByID(ticketId, myCallBack)
}