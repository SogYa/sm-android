package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class GetAllStatesUseCase(
    private val localDataBaseRepository: LocalDataBaseRepository
) {

    operator fun invoke(serverUri: String) = localDataBaseRepository.getAllStates(serverUri)
}