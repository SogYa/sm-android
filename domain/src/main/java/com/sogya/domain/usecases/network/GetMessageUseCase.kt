package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkRepository

class GetMessageUseCase(
    private val networkRepository: NetworkRepository
) {
    fun invoke(baseUri: String,token: String) = networkRepository.getMessage(baseUri,token)
}