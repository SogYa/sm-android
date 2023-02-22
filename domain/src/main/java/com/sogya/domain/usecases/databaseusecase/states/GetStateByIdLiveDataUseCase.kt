package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class GetStateByIdLiveDataUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateId: String) = repository.getStateByIdLiveData(stateId)
}