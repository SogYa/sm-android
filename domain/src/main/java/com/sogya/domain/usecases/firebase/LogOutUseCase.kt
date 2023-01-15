package com.sogya.domain.usecases.firebase

import com.sogya.domain.repository.FirebaseRepository

class LogOutUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke() = repository.logOut()
}