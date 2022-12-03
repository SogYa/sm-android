package com.sogya.domain.usecases.websocketus

import com.sogya.domain.repository.WebSocketRepository

class SendMessageUseCase(
    private val webSocketRepository: WebSocketRepository
) {
    fun invoke(message: Any) = webSocketRepository.sendMessage(message)
}