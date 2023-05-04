package com.sogya.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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
import com.sogya.data.mappers.state.StatesMapper
import com.sogya.data.mappers.zones.ListZoneMapper
import com.sogya.data.mappers.zones.ZoneDomainMapper
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class LocalDataBaseRepositoryImpl(context: Context) : LocalDataBaseRepository {
    private val db = Room.databaseBuilder(
        context, LocalDataBase::class.java, "local-data-base"
    ).allowMainThreadQueries()
        .build()

    override fun getAllStates(serverUri: String): LiveData<List<StateDomain>> {
        return db.stateDao().getAllByServerId(serverUri).map {
            ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun getStateById(entityId: String): StateDomain {
        return StatesMapper(db.stateDao().getState(entityId)).toStateDomain()
    }

    override fun getStateByIdLiveData(entityId: String): LiveData<StateDomain> {
        return db.stateDao().getStateLiveData(entityId).map {
            StatesMapper(it).toStateDomain()
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

    override fun isStateInDB(stateId: String): Boolean {
        return db.stateDao().isStateExist(stateId)
    }

    override fun updateState(stateDomain: StateDomain) {
        db.stateDao().updateState(StateDomainMapper(stateDomain).toStateData())
    }

    override fun updateStates(stateList: List<StateDomain>) {
        db.stateDao().updateStates(ListOfStatesDomainMapper(stateList).toDataList())
    }

    override fun getAllGroupsByOwner(ownerId: String): LiveData<List<StateGroupDomain>> {
        return db.groupDao().getAllByOwner(ownerId).map {
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
        return db.serverDao().getAll().map {
            ListOfServersDataMapper(it).toServerDomainList()
        }
    }

    override fun getServerById(serverUri: String): ServerStateDomain {
        return ServerDataMapper(db.serverDao().getById(serverUri)).toServerDomain()
    }

    override fun insertServer(serverState: ServerStateDomain) {
        db.serverDao().insert(ServerDomainMapper(serverState).toServerData())
    }

    override fun deleteServer(serverId: String) {
        db.serverDao().delete(serverId)
    }

    override fun updateServer(serverState: ServerStateDomain) {
        db.serverDao().update(ServerDomainMapper(serverState).toServerData())
    }

    override fun getAllByGroup(groupId: Int): LiveData<List<StateDomain>> {
        return db.stateDao().getAllByGroup(groupId).map {
            ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun getAllZones(): LiveData<List<ZoneDomain>> {
        return db.zoneDao().getAllZones().map {
            ListZoneMapper(it).toDomain()
        }
    }

    override fun insertZone(zoneDomain: ZoneDomain) {
        db.zoneDao().insertZone(ZoneDomainMapper(zoneDomain).toData())
    }

    override fun deleteZone(zoneDomain: ZoneDomain) {
        db.zoneDao().deleteZone(ZoneDomainMapper(zoneDomain).toData())
    }
}