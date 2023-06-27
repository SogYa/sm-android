package ru.sogya.projects.smartrevolutionapp.di.database

import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.usecases.databaseusecase.zones.*

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ZoneDBModule {
    @Provides
    fun providesDeleteZoneUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        DeleteZoneUseCase(localDataBaseRepository)

    @Provides
    fun providesGetAllZonesUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetAllZonesUseCase(localDataBaseRepository)

    @Provides
    fun providesInsertZoneUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        InsertZoneUseCase(localDataBaseRepository)
}