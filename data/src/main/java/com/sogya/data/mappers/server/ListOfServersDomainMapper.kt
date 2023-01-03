package com.sogya.data.mappers.server

import com.sogya.data.models.ServerState
import com.sogya.domain.models.ServerStateDomain

class ListOfServersDomainMapper(
    private val serverDomainList: List<ServerStateDomain>
) {
    fun toServerDataList(): List<ServerState> {
        val list = arrayListOf<ServerState>()
        serverDomainList.forEach {
            list.add(ServerDomainMapper(it).toServerData())
        }
        return list
    }
}