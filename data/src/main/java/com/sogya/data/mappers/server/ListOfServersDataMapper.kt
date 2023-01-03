package com.sogya.data.mappers.server

import com.sogya.data.models.ServerState
import com.sogya.domain.models.ServerStateDomain

class ListOfServersDataMapper(
    private val serverDataList: List<ServerState>
) {
    fun toServerDomainList(): List<ServerStateDomain> {
        val list = arrayListOf<ServerStateDomain>()
        serverDataList.forEach {
            list.add(ServerDataMapper(it).toServerDomain())
        }
        return list
    }
}