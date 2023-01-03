package com.sogya.domain.usecases.databaseusecase.servers

import com.sogya.domain.repository.LocalDataBaseRepository

class GetAllServersUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke() = repository.getAllServers()
}