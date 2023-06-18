package ru.sogya.projects.smartrevolutionapp.di.database

import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.usecases.databaseusecase.servers.*

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ServerDBModule {
    @Provides
    fun providesDeleteServerUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        DeleteServerUseCase(localDataBaseRepository)

    @Provides
    fun providesGetAllServersUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetAllServersUseCase(localDataBaseRepository)

    @Provides
    fun providesGetServerByIdUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetServerByIdUseCase(localDataBaseRepository)

    @Provides
    fun providesInsertServerUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        InsertServerUseCase(localDataBaseRepository)

    @Provides
    fun providesUpdateServerUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        UpdateServerUseCase(localDataBaseRepository)
}