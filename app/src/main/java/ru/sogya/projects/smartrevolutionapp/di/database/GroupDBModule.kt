package ru.sogya.projects.smartrevolutionapp.di.database

import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.usecases.databaseusecase.groups.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class GroupDBModule {
    @Provides
    fun providesDeleteGroupUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        DeleteGroupUseCase(localDataBaseRepository)

    @Provides
    fun providesGetAllGroupByOwnerUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        GetAllGroupByOwnerUseCase(localDataBaseRepository)

    @Provides
    fun providesInsertGroupUseCase(localDataBaseRepository: LocalDataBaseRepository) =
        InsertGroupUseCase(localDataBaseRepository)
}