package com.sogya.domain.usecases.databaseusecase.states

import com.sogya.domain.repository.LocalDataBaseRepository

class GetAllByGroupIdUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(groupId: Int) = repository.getAllByGroup(groupId)
}