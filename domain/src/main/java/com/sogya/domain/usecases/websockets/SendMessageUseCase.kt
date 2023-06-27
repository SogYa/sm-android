package com.sogya.domain.usecases.websockets

import com.sogya.domain.repository.WebSocketRepository

class SendMessageUseCase(
    private val webSocketRepository: WebSocketRepository
) {
    fun invoke(message: Any) = webSocketRepository.sendMessage(message)
}