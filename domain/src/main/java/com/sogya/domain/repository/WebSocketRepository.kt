package com.sogya.domain.repository

interface WebSocketRepository {
    fun init(baseUrl: String, _messageListener: MessageListener)
    fun sendMessage(message: Any): Boolean
    fun close()
    fun subscribeTrigger()
    fun reconnect()

//    fun getStates(): List<State<Any>>
}