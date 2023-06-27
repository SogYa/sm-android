package ru.sogya.projects.smartrevolutionapp.di.database

import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.usecases.databaseusecase.states.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class StateDBModule {
    @Provides
    fun providesCheckStateExistUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        CheckStateExistUseCase(localDataBaseRepository)

    @Provides
    fun providesDeleteStateUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        DeleteStateUseCase(localDataBaseRepository)

    @Provides
    fun providesGetAllByGroupIdUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetAllByGroupIdUseCase(localDataBaseRepository)

    @Provides
    fun providesGetAllStatesUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetAllStatesUseCase(localDataBaseRepository)

    @Provides
    fun providesGetStateByIdLiveDataUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetStateByIdLiveDataUseCase(localDataBaseRepository)

    @Provides
    fun providesGetStateByIdUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetStateByIdUseCase(localDataBaseRepository)

    @Provides
    fun providesInsertStateUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        InsertStateUseCase(localDataBaseRepository)

    @Provides
    fun providesUpdateStatesUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        UpdateStatesUseCase(localDataBaseRepository)

    @Provides
    fun providesUpdateStateUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        UpdateStateUseCase(localDataBaseRepository)

    @Provides
    fun providesInsertOneStateUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        InsertOneStateUseCase(localDataBaseRepository)
}