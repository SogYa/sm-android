package com.sogya.domain.usecases.databaseusecase

import com.sogya.domain.repository.LocalDataBaseRepository

class GetAllStatesUseCase(
    private val localDataBaseRepository: LocalDataBaseRepository
) {

    fun invoke() = localDataBaseRepository.getAllStates()
}