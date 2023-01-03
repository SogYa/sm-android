package com.sogya.data.mappers.server

import com.sogya.data.models.ServerState
import com.sogya.domain.models.ServerStateDomain

class ServerDataMapper(
    private val serverState: ServerState
) {
    fun toServerDomain() = ServerStateDomain(
        serverState.serverName,
        serverState.serverUri,
        serverState.serverToken
    )
}