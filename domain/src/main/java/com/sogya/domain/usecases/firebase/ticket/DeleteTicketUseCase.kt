package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.repository.FirebaseRepository

class DeleteTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(ticketId: String) = repository.deleteTicketById(ticketId)
}