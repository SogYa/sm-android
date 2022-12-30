package com.sogya.domain.usecases.databaseusecase

import com.sogya.domain.repository.LocalDataBaseRepository

class DeleteStateUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateId: String) = repository.deleteState(stateId)
}