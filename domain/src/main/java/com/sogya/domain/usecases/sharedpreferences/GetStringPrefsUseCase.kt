package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class GetStringPrefsUseCase(private val repository: SharedPreferencesRepository) {

    operator fun invoke(key: String) = repository.getStringPrefs(key)
}