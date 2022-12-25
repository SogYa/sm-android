package com.sogya.data.repository

import android.content.Context
import androidx.room.Room
import com.sogya.data.database.LocalDataBase
import com.sogya.data.mappers.ListOfStatesMapper
import com.sogya.data.mappers.StateDomainMapper
import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository
import io.reactivex.Single

class LocalDataBaseRepositoryImpl(context: Context) : LocalDataBaseRepository {
    private val db = Room.databaseBuilder(
        context, LocalDataBase::class.java, "local-data-base"
    ).build()

    override fun getAllStates(): Single<List<StateDomain>> {
        return db.stateDao().getAll().map {
            return@map ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun getStateById(entityId: String): Single<List<StateDomain>> {
        return db.stateDao().getState(entityId).map {
            return@map ListOfStatesMapper(it).toDomainList()
        }
    }

    override fun insertState(state: StateDomain) {
        return db.stateDao().insert(StateDomainMapper(state).toStateData())
    }

    override fun deleteState(state: StateDomain) {
        // return db.stateDao().delete()
    }
}