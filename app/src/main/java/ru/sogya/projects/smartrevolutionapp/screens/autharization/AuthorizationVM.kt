package ru.sogya.projects.smartrevolutionapp.screens.autharization

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.models.requests.AuthMessage
import com.sogya.data.models.requests.LongLivedRequest
import com.sogya.domain.models.DeviceDataDomain
import com.sogya.domain.models.IntegrationResponseDomain
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.TokenInfo
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.usecases.databaseusecase.servers.InsertServerUseCase
import com.sogya.domain.usecases.network.GetTokenUseCase
import com.sogya.domain.usecases.network.SendAppIntegrationUseCase
import com.sogya.domain.usecases.sharedpreferences.UpdatePrefsUseCase
import com.sogya.domain.usecases.websocketus.CloseUseCase
import com.sogya.domain.usecases.websocketus.InitUseCase
import com.sogya.domain.usecases.websocketus.SendMessageUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import kotlin.concurrent.thread


class AuthorizationVM : ViewModel(), MessageListener {
    private val networkRepository = App.getNetworkRepository()
    private val webSocketRepository = App.getWebSocketRepository()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val updatePrefsUseCase = UpdatePrefsUseCase(sharedPreferencesRepository)
    private val getTokenUseCase = GetTokenUseCase(networkRepository)
    private val sendAppIntegrationUseCase = SendAppIntegrationUseCase(networkRepository)
    private val initUseCase = InitUseCase(webSocketRepository)
    private val sendMessageUseCase = SendMessageUseCase(webSocketRepository)
    private val insertServerUseCase = InsertServerUseCase(App.getRoom())
    private val closeWebSocketUseCase = CloseUseCase(webSocketRepository)
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
        getTokenUseCase(baseUri, authCode).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<TokenInfo>() {
                override fun onSuccess(t: TokenInfo) {
                    Log.d("TOKEN", t.access_token)
                    updatePrefsUseCase.invoke(Constants.SERVER_URI, baseUri)
                    updatePrefsUseCase.invoke(Constants.SERVER_NAME, serverName)
                    serverToken = t.access_token
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

                override fun onError(e: Throwable) {
                    Log.d("TOKEN_ERROR", e.message.toString())
                    myCallBack.error()
                }
            })
    }

    fun startTestMode() {
        updatePrefsUseCase.invoke(Constants.TEST_MODE, true)
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
            webSocketRepository.close()
        }
    }

    private fun invokeIntegration() {
        sendAppIntegrationUseCase.invoke(serverUri,serverToken, getDeviceData()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<IntegrationResponseDomain>() {
                override fun onSuccess(t: IntegrationResponseDomain) {
                    updatePrefsUseCase.invoke(Constants.INTEGRATION_SECRET, t.token)
                    updatePrefsUseCase.invoke(Constants.INTEGRATION_WEB_HOOK, t.webhookId)
                }

                override fun onError(e: Throwable) {
                    Log.d("IntegrationError", e.message.toString())
                }

            })
    }

    fun getLoadingLiveData(): LiveData<Int> = loadScreenLiveData
    fun getNavigationLiveData(): LiveData<Boolean> = navigationLiveData
    fun closeWebSocket() {
        closeWebSocketUseCase.invoke()
    }
}