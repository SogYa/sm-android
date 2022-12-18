package com.sogya.domain.repository

import com.sogya.domain.models.Message
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.TokenInfo
import io.reactivex.Single

interface NetworkRepository {

    fun getMessage(baseUri: String, token: String): Single<Message>
    fun getToken(baseUri: String, authCode: String): Single<TokenInfo>
    fun getStates(baseUri: String, token: String): Single<List<StateDomain>>
}