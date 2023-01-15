package com.sogya.domain.usecases.firebase

import com.sogya.domain.repository.FirebaseRepository

class CreateUserUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(email: String, password: String) = repository.createUser(email, password)
}