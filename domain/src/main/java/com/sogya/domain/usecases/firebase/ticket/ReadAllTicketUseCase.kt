package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.models.TicketDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class ReadAllTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(myCallBack: MyCallBack<TicketDomain>) = repository.readAllTickets(myCallBack)
}