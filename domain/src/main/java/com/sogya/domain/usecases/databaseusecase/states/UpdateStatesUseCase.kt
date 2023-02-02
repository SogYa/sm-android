package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class UpdateStatesUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateList: List<StateDomain>) = repository.updateStates(stateList)
}