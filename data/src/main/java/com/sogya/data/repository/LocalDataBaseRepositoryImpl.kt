package com.sogya.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.sogya.data.database.LocalDataBase
import com.sogya.data.mappers.server.ListOfServersDataMapper
import com.sogya.data.mappers.server.ServerDataMapper
import com.sogya.data.mappers.server.ServerDomainMapper
import com.sogya.data.mappers.state.ListOfStatesDomainMapper
import com.sogya.data.mappers.state.ListOfStatesMapper
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class LocalDataBaseRepositoryImpl(context: Context) : LocalDataBaseRepository {
    private val db = Room.databaseBuilder(
        context, LocalDataBase::class.java, "local-data-base"
    ).allowMainThreadQueries()
        .build()

    override fun getAllStates(): LiveData<List<StateDomain>> {
        return Transformations.map(db.stateDao().getAll()) {
            ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun getStateById(entityId: String): LiveData<List<StateDomain>> {
        return Transformations.map(db.stateDao().getState(entityId)) {
            ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun insertState(states: List<StateDomain>) {
        return db.stateDao().insert(ListOfStatesDomainMapper(states).toDataList())
    }

    override fun deleteState(stateId: String) {
        return db.stateDao().delete(stateId)
    }

    override fun getAllServers(): LiveData<List<ServerStateDomain>> {
        return Transformations.map(db.serverDao().getAll()) {
            ListOfServersDataMapper(it).toServerDomainList()
        }
    }

    override fun getServerById(serverUri: String): ServerStateDomain {
        return ServerDataMapper(db.serverDao().getById(serverUri)).toServerDomain()
    }

    override fun insertServer(serverState: ServerStateDomain) {
        db.serverDao().insert(ServerDomainMapper(serverState).toServerData())
    }

    override fun deleteServer(serverState: ServerStateDomain) {
        db.serverDao().delete(ServerDomainMapper(serverState).toServerData())
    }

    override fun updateServer(serverState: ServerStateDomain) {
        db.serverDao().update(ServerDomainMapper(serverState).toServerData())
    }
}