package com.sogya.domain.repository

import com.sogya.domain.models.StateDomain
import io.reactivex.Single

interface LocalDataBaseRepository {

    fun getAllStates(): Single<List<StateDomain>>


    fun getStateById(entityId: String): Single<List<StateDomain>>


    fun insertState(state: StateDomain)


    fun deleteState(state: StateDomain)
}