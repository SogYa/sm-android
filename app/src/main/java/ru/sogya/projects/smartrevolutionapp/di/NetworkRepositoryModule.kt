package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.NetworkRepository
import com.sogya.domain.usecases.network.GetStateByIdUseCase
import com.sogya.domain.usecases.network.GetStateHistoryUseCase
import com.sogya.domain.usecases.network.GetStatesUseCase
import com.sogya.domain.usecases.network.GetTokenUseCase
import com.sogya.domain.usecases.network.SendAppIntegrationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NetworkRepositoryModule {

    @Provides
    fun providesGetStateByIdUseCase(networkRepository: NetworkRepository) =
        GetStateByIdUseCase(networkRepository)

    @Provides
    fun providesGetStateHistoryUseCase(networkRepository: NetworkRepository) =
        GetStateHistoryUseCase(networkRepository)

    @Provides
    fun providesGetStatesUseCase(networkRepository: NetworkRepository) =
        GetStatesUseCase(networkRepository)

    @Provides
    fun providesGetTokenUseCase(networkRepository: NetworkRepository) =
        GetTokenUseCase(networkRepository)

    @Provides
    fun providesSendAppIntegrationUseCase(networkRepository: NetworkRepository) =
        SendAppIntegrationUseCase(networkRepository)
}