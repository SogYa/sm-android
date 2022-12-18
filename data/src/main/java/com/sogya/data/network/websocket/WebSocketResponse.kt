package com.sogya.data.network.websocket

data class SocketResponse(
    val id: Long?,
    val type: String,
    val success: Boolean?,
    val result: String?,
    val event: String?,
    val haVersion: String?
)
