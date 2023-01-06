package com.sogya.domain.repository


import androidx.lifecycle.LiveData
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain

interface LocalDataBaseRepository {

    fun getAllStates(serverUri: String): LiveData<List<StateDomain>>

    fun getStateById(entityId: String): LiveData<List<StateDomain>>

    fun insertState(states: List<StateDomain>)

    fun insertOneState(states: StateDomain)

    fun deleteState(stateId: String)

    fun getAllServers(): LiveData<List<ServerStateDomain>>

    fun getServerById(serverUri: String): ServerStateDomain

    fun insertServer(serverState: ServerStateDomain)

    fun deleteServer(serverState: ServerStateDomain)

    fun updateServer(serverState: ServerStateDomain)
}