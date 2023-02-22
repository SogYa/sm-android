package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkRepository

class GetStatesUseCase(
    private val networkRepository: NetworkRepository
) {
    fun invoke(baseUri: String, token: String) =
        networkRepository.getStates(baseUri, "Bearer $token")
}