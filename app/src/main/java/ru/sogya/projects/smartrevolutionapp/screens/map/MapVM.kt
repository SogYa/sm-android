package ru.sogya.projects.smartrevolutionapp.screens.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import ru.sogya.projects.smartrevolutionapp.app.App

class MapVM : ViewModel() {
    private val sharedPrefsRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPrefsRepository)
    private val uriLiveData = MutableLiveData<String>()

    init {

    }
}