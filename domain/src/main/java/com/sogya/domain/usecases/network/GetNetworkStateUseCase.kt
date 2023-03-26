package com.sogya.domain.usecases.network

import com.sogya.domain.repository.NetworkStatesRepository

class GetNetworkStateUseCase(private val networkStatesRepository: NetworkStatesRepository) {
    fun invoke() = networkStatesRepository.getNetworkStatus()
}