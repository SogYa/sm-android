package ru.sogya.projects.smartrevolutionapp.di

import android.content.Context
import com.sogya.data.repository.FirebaseRepositoryImpl
import com.sogya.data.repository.LocalDataBaseRepositoryImpl
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.repository.NetworkStatesRepositoryImpl
import com.sogya.data.repository.SharedPreferencesRepositoryImpl
import com.sogya.data.repository.WebSocketRepositoryImpl
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.repository.NetworkRepository
import com.sogya.domain.repository.NetworkStatesRepository
import com.sogya.domain.repository.SharedPreferencesRepository
import com.sogya.domain.repository.WebSocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideNetworkRepository(): NetworkRepository = NetworkRepositoryImpl()

    @Provides
    @Singleton
    fun provideFirebaseRepository(): FirebaseRepository = FirebaseRepositoryImpl()

    @Provides
    @Singleton
    fun provideLocalDatabaseRepository(@ApplicationContext context: Context): LocalDataBaseRepository =
        LocalDataBaseRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideNetworkStatesRepository(@ApplicationContext context: Context): NetworkStatesRepository =
        NetworkStatesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideWebSocketRepository(): WebSocketRepository =
        WebSocketRepositoryImpl()
}