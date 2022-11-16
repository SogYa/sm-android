package com.sogya.domain.repository

import io.reactivex.Single
import com.sogya.domain.models.Message
import com.sogya.domain.models.TokenInfo

interface NetworkRepository {

    fun getMessage(baseUri: String, token: String): Single<Message>
    fun getToken(baseUri: String, authCode: String): Single<TokenInfo>
}