package com.sogya.domain.usecases.databaseusecase.zones

import com.sogya.domain.repository.LocalDataBaseRepository

class GetAllZonesUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke() = repository.getAllZones()
}