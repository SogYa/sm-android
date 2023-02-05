package com.sogya.domain.usecases.sharedpreferences

import com.sogya.domain.repository.SharedPreferencesRepository

class GetLongPrefsUseCase (private val repository: SharedPreferencesRepository) {

    fun invoke(key: String) = repository.getLongPrefs(key)
}