package com.sogya.data.mappers.server

import com.sogya.data.models.ServerState
import com.sogya.domain.models.ServerStateDomain

class ServerDomainMapper(
    private val serverStateDomain: ServerStateDomain
) {
    fun toServerData() = ServerState(
        serverStateDomain.serverUri,
        serverStateDomain.serverName,
        serverStateDomain.serverToken
    )
}