package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class GetDoublePrefsUseCase(private val repository: SharedPreferencesRepository) {

    fun invoke(key: String) = repository.getDoublePrefs(key)
}