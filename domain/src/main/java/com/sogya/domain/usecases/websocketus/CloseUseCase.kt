package com.sogya.domain.usecases.websocketus

import com.sogya.domain.repository.WebSocketRepository

class CloseUseCase(
    private val repository: WebSocketRepository
) {
    fun invoke() = repository.close()
}