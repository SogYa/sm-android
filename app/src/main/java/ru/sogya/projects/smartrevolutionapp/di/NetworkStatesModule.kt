package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.NetworkStatesRepository
import com.sogya.domain.usecases.network.GetNetworkStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NetworkStatesModule {

    @Provides
    fun provideNetworkStatesUseCase(networkStatesRepository: NetworkStatesRepository) =
        GetNetworkStateUseCase(networkStatesRepository)
}