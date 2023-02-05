package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class UpdatePrefsUseCase(private val repository: SharedPreferencesRepository) {

    fun invoke(key: String, value: Any) = repository.updatePrefs(key, value)
}