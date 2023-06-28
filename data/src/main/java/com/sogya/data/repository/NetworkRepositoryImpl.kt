package com.sogya.data.repository

import com.sogya.data.models.requests.IntegrationRequestData
import com.sogya.data.network.api.NetworkService
import com.sogya.domain.models.DeviceDataDomain
import com.sogya.domain.models.IntegrationResponseDomain
import com.sogya.domain.models.MessageDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.TokenInfo
import com.sogya.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl : NetworkRepository {

    override suspend fun getMessage(baseUri: String, token: String): Flow<MessageDomain> = flow {
        emit(NetworkService.getRetrofitService(baseUri).getApiMessage(token))
    }

    override suspend fun getToken(baseUri: String, authCode: String): Flow<TokenInfo> = flow {
        emit(
            NetworkService.getRetrofitService(baseUri)
                .getApiToken("authorization_code", authCode, baseUri)
        )
    }

    override suspend fun getStates(baseUri: String, token: String): Flow<List<StateDomain>> = flow {
        emit(
            NetworkService.getRetrofitService(baseUri)
                .getApiStates(token)
        )
    }

    override suspend fun getStateById(
        baseUri: String,
        token: String,
        entityId: String
    ): Flow<StateDomain> = flow {
        emit(NetworkService.getRetrofitService(baseUri).getStateById(token, entityId))
    }

    override suspend fun sendAppIntegration(
        baseUri: String,
        token: String,
        deviceData: DeviceDataDomain
    ): Flow<IntegrationResponseDomain> = flow {
        emit(
            NetworkService.getRetrofitService(baseUri).registrationMobileAppIntegration(
                token,
                IntegrationRequestData(
                    deviceId = deviceData.deviceId,
                    deviceName = deviceData.deviceName,
                    manufacturer = deviceData.manufacturer,
                    model = deviceData.model,
                    operationSystemName = deviceData.operationSystemName,
                    operationSystemVersion = deviceData.operationSystemVersion
                )
            )
        )
    }

    override suspend fun getStateHistory(
        baseUri: String,
        token: String,
        timestamp: String,
        entityId: String
    ): Flow<List<StateDomain>> = flow {
        emit(
            NetworkService.getRetrofitService(baseUri)
                .getStateHistory(token, timestamp, entityId)
        )

    }
}