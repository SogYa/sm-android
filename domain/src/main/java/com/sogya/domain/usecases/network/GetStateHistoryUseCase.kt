package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkRepository

class GetStateHistoryUseCase(
    private val repository: NetworkRepository
) {
    operator fun invoke(baseUri: String, token: String, timestamp: String, entityId: String) =
        repository.getStateHistory(baseUri, token, timestamp, entityId)
}