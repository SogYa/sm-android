package com.sogya.domain.usecases.websockets

import com.sogya.domain.repository.MessageListener
import com.sogya.domain.repository.WebSocketRepository

class InitUseCase(
    private val webSocketRepository: WebSocketRepository
) {
    fun invoke(baseUrl: String, _messageListener: MessageListener) =
        webSocketRepository.init(baseUrl, _messageListener)
}