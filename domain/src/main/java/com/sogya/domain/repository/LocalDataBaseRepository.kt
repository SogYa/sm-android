package com.sogya.domain.repository


import androidx.lifecycle.LiveData
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain

interface LocalDataBaseRepository {

    fun getAllStates(): LiveData<List<StateDomain>>

    fun getStateById(entityId: String): LiveData<List<StateDomain>>

    fun insertState(states: List<StateDomain>)

    fun deleteState(stateId: String)

    fun getAllServers(): LiveData<ServerStateDomain>

    fun getServersById(serverId: Int): LiveData<ServerStateDomain>

    fun insertServer(serverState: ServerStateDomain): LiveData<Boolean>

    fun deleteServer(serverState: ServerStateDomain): LiveData<Boolean>

    fun updateServer(serverState: ServerStateDomain)
}