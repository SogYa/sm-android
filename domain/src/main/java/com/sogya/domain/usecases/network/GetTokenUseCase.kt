package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkRepository

class GetTokenUseCase(
    private val repository: NetworkRepository
) {

    fun invoke(baseUri: String, authCode: String) = repository.getToken(baseUri, authCode)
}