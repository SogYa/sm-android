package ru.sogya.projects.smartrevolutionapp.screens.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.utils.Constants
import com.sogya.data.utils.MyCallBack
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.usecases.databaseusecase.servers.DeleteServerUseCase
import com.sogya.domain.usecases.databaseusecase.servers.GetAllServersUseCase
import com.sogya.domain.usecases.databaseusecase.servers.GetServerByIdUseCase
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class ServersVM : ViewModel() {
    private var serverLiveData: LiveData<List<ServerStateDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getAllServersUseCase = GetAllServersUseCase(repository)
    private val getServerByIdUseCase = GetServerByIdUseCase(repository)
    private val deleteServerUseCase = DeleteServerUseCase(repository)

    fun getServerLiveData(): LiveData<List<ServerStateDomain>> = serverLiveData

    init {
        serverLiveData = getAllServersUseCase.invoke()
    }

    fun getServer(serverId: String,myCallBack: MyCallBack<Boolean>) {
        viewModelScope.launch {
            val job = launch {
                val server = getServerByIdUseCase.invoke(serverId)
                SPControl.getInstance().updatePrefs(Constants.SERVER_URI, server.serverUri)
                SPControl.getInstance().updatePrefs(Constants.AUTH_TOKEN, server.serverToken)
                SPControl.getInstance().updatePrefs(Constants.SERVER_NAME, server.serverName)
            }
            job.join()
            myCallBack.data(true)
        }
    }

    fun removeServer(serverId: String) {
        viewModelScope.launch {
            deleteServerUseCase.invoke(serverId)
        }
    }
}