package ru.sogya.projects.smartrevolutionapp.screens.autharization

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.models.requests.AuthMessage
import com.sogya.data.models.requests.LongLivedRequest
import com.sogya.domain.models.DeviceDataDomain
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.usecases.databaseusecase.servers.InsertServerUseCase
import com.sogya.domain.usecases.network.GetTokenUseCase
import com.sogya.domain.usecases.network.SendAppIntegrationUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.usecases.websockets.CloseUseCase
import com.sogya.domain.usecases.websockets.InitUseCase
import com.sogya.domain.usecases.websockets.SendMessageUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class AuthorizationVM @Inject constructor(
    private val updatePrefsUseCase: UpdatePrefsUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val sendAppIntegrationUseCase: SendAppIntegrationUseCase,
    private val initUseCase: InitUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val insertServerUseCase: InsertServerUseCase,
    private val closeWebSocketUseCase: CloseUseCase,
) : ViewModel(), MessageListener {

    private lateinit var serverToken: String
    private lateinit var serverUri: String
    private lateinit var serverTag: String
    private val loadScreenLiveData = MutableLiveData<Int>()
    private val navigationLiveData = MutableLiveData<Boolean>()


    fun getToken(
        serverName: String,
        baseUri: String,
        authCode: String,
        myCallBack: MyCallBack<Boolean>
    ) {
        viewModelScope.launch {
            getTokenUseCase(baseUri, authCode).flowOn(Dispatchers.IO)
                .catch {
                    Log.d("TOKEN_ERROR", it.message.toString())
                    myCallBack.error()
                }
                .collect() {
                    Log.d("TOKEN", it.access_token)
                    updatePrefsUseCase.invoke(Constants.SERVER_URI, baseUri)
                    updatePrefsUseCase.invoke(Constants.SERVER_NAME, serverName)
                    serverToken = it.access_token
                    serverTag = serverName
                    serverUri = baseUri
                    thread {
                        kotlin.run {
                            initUseCase.invoke(
                                "${baseUri}/api/websocket",
                                this@AuthorizationVM
                            )
                        }
                    }
                    loadScreenLiveData.postValue(VisibilityStates.VISIBLE.visibility)
                    myCallBack.data(true)
                }
        }
    }

    private fun getDeviceData() = DeviceDataDomain(
        Build.ID,
        Build.DEVICE,
        Build.MANUFACTURER,
        Build.MODEL,
        Build.VERSION.CODENAME,
        Build.VERSION.RELEASE
    )

    override fun onConnectSuccess() {
        Log.d("WEBSUCCES", "Connected")
        loadScreenLiveData.postValue(VisibilityStates.VISIBLE.visibility)
        sendMessageUseCase.invoke(
            AuthMessage(
                token = serverToken
            )
        )
    }

    override fun onConnectFailed() {
        Log.d("", "Connection Failed")
    }

    override fun onClose() {
        Log.d("WEBSOCKETCLOSE", "Closed")
    }

    override fun onMessage(text: String?) {
        Log.d("WEBM", text.toString())
        val result = JSONObject(text.toString())
        if (result.get("type") == "auth_ok") {
            sendMessageUseCase.invoke(LongLivedRequest())

        } else if (result.get("type") == "result") {
            serverToken = result.get("result").toString()
            invokeIntegration()
            updatePrefsUseCase.invoke(Constants.AUTH_TOKEN, serverToken)
            insertServerUseCase.invoke(
                serverStateDomain = ServerStateDomain(
                    serverTag, serverUri, serverToken
                )
            )
            closeWebSocketUseCase.invoke()
            loadScreenLiveData.postValue(VisibilityStates.VISIBLE.visibility)
            navigationLiveData.postValue(true)
        }
    }

    private fun invokeIntegration() {
        viewModelScope.launch {
            sendAppIntegrationUseCase.invoke(serverUri, serverToken, getDeviceData())
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d("IntegrationError", it.message.toString())
                }
                .collect {
                    updatePrefsUseCase.invoke(Constants.INTEGRATION_SECRET, it.token)
                    updatePrefsUseCase.invoke(Constants.INTEGRATION_WEB_HOOK, it.webhookId)
                }
        }
    }
    fun getLoadingLiveData(): LiveData<Int> = loadScreenLiveData
    fun getNavigationLiveData(): LiveData<Boolean> = navigationLiveData
    fun closeWebSocket() {
        closeWebSocketUseCase.invoke()
    }
}