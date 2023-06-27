package com.sogya.domain.usecases.websockets

import com.sogya.domain.repository.WebSocketRepository

class ReconnectUseCase(
    private val repository: WebSocketRepository
) {
    fun invoke() = repository.reconnect()
}