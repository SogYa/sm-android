package com.sogya.domain.repository


import androidx.lifecycle.LiveData
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDataBaseRepository {

    fun getAllStates(): Flowable<List<StateDomain>>

    fun getStateById(entityId: String): Single<List<StateDomain>>

    fun insertState(states: List<StateDomain>)

    fun deleteState(stateId: String)

    fun getAll(): LiveData<ServerStateDomain>

    fun getById(serverId: Int): LiveData<ServerStateDomain>

    fun insert(serverState: ServerStateDomain): LiveData<Boolean>

    fun delete(serverState: ServerStateDomain): LiveData<Boolean>
}