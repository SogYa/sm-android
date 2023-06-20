package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.WebSocketRepository
import com.sogya.domain.usecases.websockets.*

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WebSocketRepositoryModule {
    @Provides
    fun providesCloseUseCase(webSocketRepository: WebSocketRepository) =
        CloseUseCase(webSocketRepository)

    @Provides
    fun providesInitUseCase(webSocketRepository: WebSocketRepository) =
        InitUseCase(webSocketRepository)

    @Provides
    fun providesReconnectUseCase(webSocketRepository: WebSocketRepository) =
        ReconnectUseCase(webSocketRepository)

    @Provides
    fun providesSendMessageUseCase(webSocketRepository: WebSocketRepository) =
        SendMessageUseCase(webSocketRepository)
}