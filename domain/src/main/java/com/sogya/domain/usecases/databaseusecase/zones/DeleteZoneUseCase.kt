package com.sogya.domain.usecases.databaseusecase.zones

import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.repository.LocalDataBaseRepository

class DeleteZoneUseCase(
    private val repository: LocalDataBaseRepository
) {
    fun invoke(zoneDomain: ZoneDomain) = repository.deleteZone(zoneDomain)
}