package com.sogya.data.repository

import com.sogya.data.mappers.message.MessageMapper
import com.sogya.data.mappers.state.ListOfStatesMapper
import com.sogya.data.models.requests.IntegrationRequestData
import com.sogya.data.network.api.NetworkService
import com.sogya.domain.models.*
import com.sogya.domain.repository.NetworkRepository
import io.reactivex.Single

class NetworkRepositoryImpl : NetworkRepository {

    override fun getMessage(baseUri: String, token: String): Single<Message> {
        return NetworkService.getRetrofitService(baseUri).getApiMessage(token).map {
            return@map MessageMapper(it).toMessageDomian()
        }
    }

    override fun getToken(baseUri: String, authCode: String): Single<TokenInfo> {
        return NetworkService.getRetrofitService(baseUri)
            .getApiToken("authorization_code", authCode, baseUri)
    }

    override fun getStates(baseUri: String, token: String): Single<List<StateDomain>> {
        return NetworkService.getRetrofitService(baseUri)
            .getApiStates(token).map {
                return@map ListOfStatesMapper(it).toDomainList()
            }
    }

    override fun sendAppIntegration(
        baseUri: String,
        token: String,
        deviceData: DeviceDataDomain
    ): Single<IntegrationResponseDomain> {
        return NetworkService.getRetrofitService(baseUri).registrationMobileAppIntegration(
            token,
            IntegrationRequestData(
                deviceId = deviceData.deviceId,
                deviceName = deviceData.deviceName,
                manufacturer = deviceData.manufacturer,
                model = deviceData.model,
                operationSystemName = deviceData.operationSystemName,
                operationSystemVersion = deviceData.operationSystemVersion
            )
        ).map {
            return@map it.toDomain()
        }
    }
}