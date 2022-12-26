package com.sogya.domain.models

data class ServerStateDomain(
    val serverId: Int,
    val serverName: String,
    val serverUri: String,
    val serverToken: String
)
