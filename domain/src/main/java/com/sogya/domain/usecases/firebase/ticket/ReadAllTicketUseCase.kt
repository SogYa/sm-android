package com.sogya.domain.usecases.firebase.ticket

import com.sogya.domain.repository.FirebaseRepository

class ReadAllTicketUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke() = repository.readAllTickets()
}