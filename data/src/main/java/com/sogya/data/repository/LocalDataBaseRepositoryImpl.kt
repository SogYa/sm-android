package com.sogya.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.sogya.data.database.LocalDataBase
import com.sogya.data.mappers.ListOfStatesDomainMapper
import com.sogya.data.mappers.ListOfStatesMapper
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

    override fun getAllServers(): LiveData<ServerStateDomain> {
        TODO("Not yet implemented")
    }

    override fun getServersById(serverId: Int): LiveData<ServerStateDomain> {
        TODO("Not yet implemented")
    }

    override fun insertServer(serverState: ServerStateDomain): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override fun deleteServer(serverState: ServerStateDomain): LiveData<Boolean> {
        TODO("Not yet implemented")
    }
}