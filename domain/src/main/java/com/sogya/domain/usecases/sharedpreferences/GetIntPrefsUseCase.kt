package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class GetIntPrefsUseCase(private val repository: SharedPreferencesRepository) {

    fun invoke(key: String) = repository.getIntPrefs(key)
}