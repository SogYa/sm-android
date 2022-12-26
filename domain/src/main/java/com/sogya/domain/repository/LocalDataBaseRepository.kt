package com.sogya.domain.repository


import androidx.lifecycle.LiveData
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import io.reactivex.Single

interface LocalDataBaseRepository {

    fun getAllStates(): Single<List<StateDomain>>

    fun getStateById(entityId: String): Single<List<StateDomain>>

    fun insertState(state: StateDomain)

    fun deleteState(state: StateDomain)

    fun getAll(): LiveData<ServerStateDomain>

    fun getById(serverId: Int): LiveData<ServerStateDomain>

    fun insert(serverState: ServerStateDomain): LiveData<Boolean>

    fun delete(serverState: ServerStateDomain): LiveData<Boolean>
}