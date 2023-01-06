package com.sogya.domain.usecases.databaseusecase.groups

import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class InsertGroupUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateGroupDomain: StateGroupDomain) = repository.insertGroup(stateGroupDomain)
}