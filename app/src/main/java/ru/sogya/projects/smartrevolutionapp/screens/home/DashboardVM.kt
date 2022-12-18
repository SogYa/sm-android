package ru.sogya.projects.smartrevolutionapp.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

class DashboardVM : ViewModel() {
    val loadingViewLiveData = MutableLiveData<Int>()


    init {
        loadingViewLiveData.postValue(VisibilityStates.GONE.visibility)
    }
}