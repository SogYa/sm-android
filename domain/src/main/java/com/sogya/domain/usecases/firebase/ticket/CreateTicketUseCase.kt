package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.repository.FirebaseRepository

class CreateTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(ticketDevice: String, ticketZone: String, ticketDesc: String?, ticketDate: String) =
        repository.createTicket(ticketDevice, ticketZone, ticketDesc, ticketDevice)
}