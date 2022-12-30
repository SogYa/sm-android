package com.sogya.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sogya.data.database.LocalDataBase
import com.sogya.data.mappers.ListOfStatesDomainMapper
import com.sogya.data.mappers.ListOfStatesMapper
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataBaseRepositoryImpl(context: Context) : LocalDataBaseRepository {
    private val db = Room.databaseBuilder(
        context, LocalDataBase::class.java, "local-data-base"
    ).build()

    override fun getAllStates(): Flowable<List<StateDomain>> {
        return db.stateDao().getAll().map {
            return@map ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun getStateById(entityId: String): Single<List<StateDomain>> {
        return db.stateDao().getState(entityId).map {
            return@map ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun insertState(states: List<StateDomain>) {
        return db.stateDao().insert(ListOfStatesDomainMapper(states).toDataList())
    }


    override fun deleteState(stateId: String) {
        return db.stateDao().delete(stateId)
    }

    override fun getAll(): LiveData<ServerStateDomain> {
        TODO("Not yet implemented")
    }

    override fun getById(serverId: Int): LiveData<ServerStateDomain> {
        TODO("Not yet implemented")
    }

    override fun insert(serverState: ServerStateDomain): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override fun delete(serverState: ServerStateDomain): LiveData<Boolean> {
        TODO("Not yet implemented")
    }
}