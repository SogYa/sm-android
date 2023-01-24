package com.sogya.data.repository

import android.util.Log
import com.google.gson.Gson
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.repository.WebSocketRepository
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit


class WebSocketRepositoryImpl : WebSocketRepository {
    companion object {
        private const val MAX_NUM = 5  // Maximum number of reconnections
        private const val MILLIS = 5000  // Reconnection interval, milliseconds
        private val TAG = WebSocketRepositoryImpl::class.java.simpleName
    }

    private lateinit var client: OkHttpClient
    private lateinit var request: Request
    private lateinit var messageListener: MessageListener
    private lateinit var mWebSocket: WebSocket
    private var isConnect = false
    private var connectNum = 0

    override fun init(baseUrl: String, _messageListener: MessageListener) {
        client = OkHttpClient.Builder()
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        request = Request.Builder().url(baseUrl).build()
        messageListener = _messageListener
        connect()
    }
     private fun connect() {
        if (isConnect()) {
            Log.i(TAG, "web socket connected")
            return
        }
        client.newWebSocket(request, createListener())
    }

     private fun disconnect(): Boolean {

        Log.d(TAG, "disconnect: called")
        isConnect = false

        connectNum = 0
        val webSocket: WebSocket = mWebSocket
        webSocket.close(1001, "Application closed")
        return true
    }

     private fun reconnect() {
        if (connectNum <= MAX_NUM) {
            try {
                Thread.sleep(MILLIS.toLong())
                connect()
                connectNum++
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else {
            Log.i(
                TAG,
                "reconnect over $MAX_NUM,please check url or network"
            )
        }
    }

    private fun isConnect(): Boolean {
        return isConnect
    }

    override fun sendMessage(message: Any): Boolean {
        return if (!isConnect()) false else mWebSocket.send(Gson().toJson(message))
    }

    override fun close() {
        if (isConnect()) {
            mWebSocket.cancel()
            mWebSocket.close(1001, "The client actively closes the connection ")
        }
    }

    private fun createListener(): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(
                webSocket: WebSocket,
                response: Response
            ) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "open:$response")
                mWebSocket = webSocket
                isConnect = response.code == 101
                if (!isConnect) {
                    reconnect()
                } else {
                    Log.i(TAG, "connect success.")
                    messageListener.onConnectSuccess()
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                messageListener.onMessage(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                messageListener.onMessage(bytes.base64())
            }

            override fun onClosing(
                webSocket: WebSocket,
                code: Int,
                reason: String
            ) {
                super.onClosing(webSocket, code, reason)
                isConnect = false
                messageListener.onClose()
            }

            override fun onClosed(
                webSocket: WebSocket,
                code: Int,
                reason: String
            ) {
                super.onClosed(webSocket, code, reason)
                isConnect = false
                messageListener.onClose()
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: Response?
            ) {
                super.onFailure(webSocket, t, response)
                if (response != null) {
                    Log.i(
                        TAG,
                        "connect failed：" + response.message
                    )
                }
                Log.i(
                    TAG,
                    "connect failed throwable：" + t.message
                )
                isConnect = false
                messageListener.onConnectFailed()
                reconnect()
            }
        }
    }
}