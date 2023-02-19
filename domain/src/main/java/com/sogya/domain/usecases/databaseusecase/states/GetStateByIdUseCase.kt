package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class GetStateByIdUseCase(
    private val localDataBaseRepository: LocalDataBaseRepository
) {
    fun invoke(entityId: String) = localDataBaseRepository.getStateById(entityId)
}