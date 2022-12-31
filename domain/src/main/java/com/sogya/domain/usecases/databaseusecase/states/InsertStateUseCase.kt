package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class InsertStateUseCase(
    private val localDataBaseRepository: LocalDataBaseRepository
) {
    fun invoke(states: List<StateDomain>) = localDataBaseRepository.insertState(states)
}