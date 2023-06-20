package ru.sogya.projects.smartrevolutionapp.screens.servers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.usecases.databaseusecase.servers.DeleteServerUseCase
import com.sogya.domain.usecases.databaseusecase.servers.GetAllServersUseCase
import com.sogya.domain.usecases.databaseusecase.servers.GetServerByIdUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServersVM @Inject constructor(
    private val updatePrefsUseCase: UpdatePrefsUseCase,
    getAllServersUseCase: GetAllServersUseCase,
    private val getServerByIdUseCase: GetServerByIdUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,
) : ViewModel() {
    private var serverLiveData: LiveData<List<ServerStateDomain>> = MutableLiveData()


    fun getServerLiveData(): LiveData<List<ServerStateDomain>> = serverLiveData

    init {
        serverLiveData = getAllServersUseCase.invoke()
    }

    fun getServer(serverId: String, myCallBack: MyCallBack<Boolean>) {
        viewModelScope.launch {
            val job = launch {
                val server = getServerByIdUseCase.invoke(serverId)
                updatePrefsUseCase.invoke(Constants.SERVER_URI, server.serverUri)
                updatePrefsUseCase.invoke(Constants.AUTH_TOKEN, server.serverToken)
                updatePrefsUseCase.invoke(Constants.SERVER_NAME, server.serverName)
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