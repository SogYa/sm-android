package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class GetBooleanPrefsUseCase(private val repository: SharedPreferencesRepository) {

    fun invoke(key: String) = repository.getBoolPrefs(key)
}