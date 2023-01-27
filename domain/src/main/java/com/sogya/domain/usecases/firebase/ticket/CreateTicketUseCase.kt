package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class CreateTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(
        ticketDevice: String,
        ticketZone: String,
        ticketDesc: String?,
        ticketDate: String,
        myCallBack: MyCallBack<Boolean>
    ) =
        repository.createTicket(
            ticketDevice,
            ticketZone,
            ticketDesc,
            ticketDate,
            myCallBack
        )
}