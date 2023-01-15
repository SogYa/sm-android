package com.sogya.domain.usecases.firebase

import com.sogya.domain.repository.FirebaseRepository

class LogInUserUseCase(
    private val repository: FirebaseRepository
) {
    fun invoke(email: String, password: String) = repository.logInUser(email,password)
}