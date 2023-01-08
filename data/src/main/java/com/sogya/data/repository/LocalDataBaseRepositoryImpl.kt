package com.sogya.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.sogya.data.database.LocalDataBase
import com.sogya.data.mappers.group.GroupDomainMapper
import com.sogya.data.mappers.group.ListOfGroupDataMapper
import com.sogya.data.mappers.server.ListOfServersDataMapper
import com.sogya.data.mappers.server.ServerDataMapper
import com.sogya.data.mappers.server.ServerDomainMapper
import com.sogya.data.mappers.state.ListOfStatesDomainMapper
import com.sogya.data.mappers.state.ListOfStatesMapper
import com.sogya.data.mappers.state.StateDomainMapper
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class LocalDataBaseRepositoryImpl(context: Context) : LocalDataBaseRepository {
    private val db = Room.databaseBuilder(
        context, LocalDataBase::class.java, "local-data-base"
    ).allowMainThreadQueries()
        .build()

    override fun getAllStates(serverUri: String): LiveData<List<StateDomain>> {
        return Transformations.map(db.stateDao().getAllByServerId(serverUri)) {
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

    override fun insertOneState(states: StateDomain) {
        db.stateDao().insertOne(StateDomainMapper(states).toStateData())
    }

    override fun deleteState(stateId: String) {
        return db.stateDao().delete(stateId)
    }

    override fun getAllGroupsByOwner(ownerId: String): LiveData<List<StateGroupDomain>> {
        return Transformations.map(db.groupDao().getAllByOwner(ownerId)) {
            ListOfGroupDataMapper(it).toDomainList()
        }
    }

    override fun insertGroup(stateGroupDomain: StateGroupDomain) {
        return db.groupDao().insertGroup(GroupDomainMapper(stateGroupDomain).toGroupData())
    }

    override fun deleteGroup(stateGroupId: Int) {
        db.stateDao().deleteGroupIdFromStets(stateGroupId)
        return db.groupDao().deleteGroup(stateGroupId)
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

    override fun getAllByGroup(groupId: Int): LiveData<List<StateDomain>> {
        return Transformations.map(db.stateDao().getAllByGroup(groupId)) {
            ListOfStatesMapper(it).toDomainList()
        }
    }
}