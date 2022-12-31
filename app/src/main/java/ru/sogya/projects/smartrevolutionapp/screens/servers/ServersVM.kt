package ru.sogya.projects.smartrevolutionapp.screens.servers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.ServerStateDomain

class ServersVM : ViewModel() {
    private val serverLiveData = MutableLiveData<List<ServerStateDomain>>()

    fun getServerLiveData(): MutableLiveData<List<ServerStateDomain>> {
        return serverLiveData
    }
}