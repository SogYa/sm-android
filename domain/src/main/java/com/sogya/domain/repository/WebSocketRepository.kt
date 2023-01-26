package com.sogya.domain.repository

import com.sogya.domain.models.TriggerEventDomain
import io.reactivex.Flowable

interface WebSocketRepository {
    fun init(baseUrl: String, _messageListener: MessageListener)
    fun sendMessage(message: Any): Boolean
    fun close()
    fun subscribeTrigger(baseUrl: String,stateId: String) : Flowable<TriggerEventDomain>
//    fun getStates(): List<State<Any>>
}