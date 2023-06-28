package com.sogya.domain.repository

import com.sogya.domain.models.DeviceDataDomain
import com.sogya.domain.models.IntegrationResponseDomain
import com.sogya.domain.models.MessageDomain
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.TokenInfo
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getMessage(baseUri: String, token: String): Flow<MessageDomain>
    suspend fun getToken(baseUri: String, authCode: String): Flow<TokenInfo>
    suspend fun getStates(baseUri: String, token: String): Flow<List<StateDomain>>
    suspend fun getStateById(baseUri: String, token: String, entityId: String): Flow<StateDomain>
    suspend fun sendAppIntegration(
        baseUri: String,
        token: String,
        deviceData: DeviceDataDomain
    ): Flow<IntegrationResponseDomain>

    suspend fun getStateHistory(
        baseUri: String,
        token: String,
        timestamp: String,
        entityId: String
    ): Flow<List<StateDomain>>
}