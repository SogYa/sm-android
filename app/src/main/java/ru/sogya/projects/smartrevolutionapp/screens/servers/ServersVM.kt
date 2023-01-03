package ru.sogya.projects.smartrevolutionapp.screens.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.usecases.databaseusecase.servers.GetAllServersUseCase
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class ServersVM : ViewModel() {
    private var serverLiveData: LiveData<List<ServerStateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getAllServersUseCase = GetAllServersUseCase(repository)

    fun getServerLiveData(): LiveData<List<ServerStateDomain>> {
        return serverLiveData
    }

    fun getServer(server: ServerStateDomain) {
        serverLiveData = getAllServersUseCase.invoke()
        SPControl.getInstance().updatePrefs(Constants.SERVER_URI, server.serverUri)
        SPControl.getInstance().updatePrefs(Constants.AUTH_TOKEN, server.serverToken)
    }
}