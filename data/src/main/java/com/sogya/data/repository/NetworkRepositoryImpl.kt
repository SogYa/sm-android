package com.sogya.data.repository

import com.sogya.data.mappers.MessageMapper
import com.sogya.data.network.api.NetworkService
import com.sogya.domain.models.Message
import com.sogya.domain.models.TokenInfo
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
}