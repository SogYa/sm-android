package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class GetStatesByIdUseCase(
    private val localDataBaseRepository: LocalDataBaseRepository
) {
    fun inovoke(entityId: String) = localDataBaseRepository.getStateById(entityId)
}