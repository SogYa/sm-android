package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class CheckStateExistUSeCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateId:String) = repository.isStateInDB(stateId)
}