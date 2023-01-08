package com.sogya.domain.usecases.databaseusecase.servers

import com.sogya.domain.repository.LocalDataBaseRepository

class DeleteServerUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(serverId: String) = repository.deleteServer(serverId)
}