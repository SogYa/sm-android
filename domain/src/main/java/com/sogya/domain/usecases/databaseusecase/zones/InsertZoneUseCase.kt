package com.sogya.domain.usecases.databaseusecase.zones

import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class InsertZoneUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(zoneDomain: ZoneDomain) = repository.insertZone(zoneDomain)
}