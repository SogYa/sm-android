package com.sogya.data.network.websocket

import com.sogya.data.models.LongLivedAccessToken
import com.sogya.data.models.TriggerEvent
import com.sogya.data.models.requests.LongLivedRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import io.reactivex.Observable

interface WebSocketApi {

    @Send
    fun subscribeLivedAccessToken(action: LongLivedRequest)

    @Receive
    fun getToken(): Observable<LongLivedAccessToken>

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun subscribeTriggerEvent()

    @Receive
    fun observerTriggersEvent(): Flowable<TriggerEvent>

}