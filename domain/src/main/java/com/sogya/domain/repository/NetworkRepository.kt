package com.sogya.domain.repository

import com.sogya.domain.models.*
import io.reactivex.Single

interface NetworkRepository {
    fun getMessage(baseUri: String, token: String): Single<Message>
    fun getToken(baseUri: String, authCode: String): Single<TokenInfo>
    fun getStates(baseUri: String, token: String): Single<List<StateDomain>>
    fun getStateById(baseUri: String, token: String,entityId:String): Single<StateDomain>
    fun sendAppIntegration(
        baseUri: String,
        token: String,
        deviceData: DeviceDataDomain
    ): Single<IntegrationResponseDomain>
}