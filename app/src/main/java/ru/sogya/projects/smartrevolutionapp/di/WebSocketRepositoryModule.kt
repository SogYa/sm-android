package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.WebSocketRepository
import com.sogya.domain.usecases.websockets.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
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