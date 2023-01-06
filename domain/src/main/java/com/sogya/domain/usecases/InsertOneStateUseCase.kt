package com.sogya.domain.usecases

import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class InsertOneStateUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(stateDomain: StateDomain) = repository.insertOneState(stateDomain)
}