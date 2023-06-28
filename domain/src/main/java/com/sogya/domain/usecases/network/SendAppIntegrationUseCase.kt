package com.sogya.domain.usecases.network

import com.sogya.domain.models.DeviceDataDomain
import com.sogya.domain.repository.NetworkRepository

class SendAppIntegrationUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(baseUri:String, token: String, deviceData: DeviceDataDomain) =
        networkRepository.sendAppIntegration(baseUri,"Bearer $token", deviceData)
}