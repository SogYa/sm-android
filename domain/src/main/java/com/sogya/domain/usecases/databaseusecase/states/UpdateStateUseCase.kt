package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class UpdateStateUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateDomain: StateDomain) = repository.updateState(stateDomain)
}