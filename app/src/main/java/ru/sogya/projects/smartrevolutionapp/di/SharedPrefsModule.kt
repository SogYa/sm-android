package ru.sogya.projects.smartrevolutionapp.di

import com.sogya.domain.repository.SharedPreferencesRepository
import com.sogya.domain.usecases.sharedpreferences.GetBooleanPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetDoublePrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetIntPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetLongPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule {
    @Provides
    fun providesGetBooleanPrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        GetBooleanPrefsUseCase(sharedPreferencesRepository)
    @Provides
    fun providesGetDoublePrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        GetDoublePrefsUseCase(sharedPreferencesRepository)

    @Provides
    fun providesGetIntPrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        GetIntPrefsUseCase(sharedPreferencesRepository)

    @Provides
    fun providesGetLongPrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        GetLongPrefsUseCase(sharedPreferencesRepository)

    @Provides
    fun providesGetStringPrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        GetStringPrefsUseCase(sharedPreferencesRepository)

    @Provides
    fun providesUpdatePrefsUseCase(sharedPreferencesRepository: SharedPreferencesRepository) =
        UpdatePrefsUseCase(sharedPreferencesRepository)
}