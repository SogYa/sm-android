package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkRepository

class GetStateByIdUseCase(
    private val repository: NetworkRepository
) {
    fun invoke(baseUri: String, token: String, entityId: String) =
        repository.getStateById(baseUri, token, entityId)
}