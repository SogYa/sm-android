package com.sogya.domain.repository

import com.sogya.domain.models.*
import io.reactivex.Single
import java.sql.Timestamp

interface NetworkRepository {
    fun getMessage(baseUri: String, token: String): Single<Message>
    fun getToken(baseUri: String, authCode: String): Single<TokenInfo>
    fun getStates(baseUri: String, token: String): Single<List<StateDomain>>
    fun getStateById(baseUri: String, token: String, entityId: String): Single<StateDomain>
    fun sendAppIntegration(
        baseUri: String,
        token: String,
        deviceData: DeviceDataDomain
    ): Single<IntegrationResponseDomain>

    fun getStateHistory(
        baseUri: String,
        token: String,
        timestamp: String,
        entityId: String
    ): Single<List<StateDomain>>
}