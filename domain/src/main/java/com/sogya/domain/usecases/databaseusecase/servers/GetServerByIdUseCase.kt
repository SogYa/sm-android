package com.sogya.domain.usecases.databaseusecase.servers

import com.sogya.domain.repository.LocalDataBaseRepository

class GetServerByIdUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(serverUri: String) = repository.getServerById(serverUri)
}