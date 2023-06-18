package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideNetworkRepository(): NetworkRepository = NetworkRepositoryImpl()
}