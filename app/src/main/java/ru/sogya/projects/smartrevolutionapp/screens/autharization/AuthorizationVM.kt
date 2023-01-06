package ru.sogya.projects.smartrevolutionapp.screens.autharization

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.models.requests.AuthMessage
import com.sogya.data.models.requests.LongLivedRequest
import com.sogya.data.repository.NetworkRepositoryImpl
import com.sogya.data.repository.WebSocketRepositoryImpl
import com.sogya.data.utils.Constants
import com.sogya.data.utils.MyCallBack
import com.sogya.domain.models.ServerStateDomain
import com.sogya.domain.models.TokenInfo
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.usecases.GetTokenUseCase
import com.sogya.domain.usecases.databaseusecase.servers.InsertServerUseCase
import com.sogya.domain.usecases.websocketus.InitUseCase
import com.sogya.domain.usecases.websocketus.SendMessageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates
import kotlin.concurrent.thread


class AuthorizationVM : ViewModel(), MessageListener {
    private val networkRepository = NetworkRepositoryImpl()
    private val webSocketRepository = WebSocketRepositoryImpl()
    private val getTokenUseCase = GetTokenUseCase(networkRepository)
    private val initUseCase = InitUseCase(webSocketRepository)
    private val sendMessageUseCase = SendMessageUseCase(webSocketRepository)
    private val insertServerUseCase = InsertServerUseCase(App.getRoom())
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
        getTokenUseCase.invoke(baseUri, authCode).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<TokenInfo>() {
                override fun onSuccess(t: TokenInfo) {
                    Log.d("TOKEN", t.access_token)
                    SPControl.getInstance().updatePrefs(Constants.SERVER_URI, baseUri)
                    SPControl.getInstance().updatePrefs(Constants.SERVER_NAME, serverName)
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
        SPControl.getInstance().updatePrefs(Constants.TEST_MODE, true)
    }

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
            SPControl.getInstance().updatePrefs(Constants.AUTH_TOKEN, serverToken)
            insertServerUseCase.invoke(
                serverStateDomain = ServerStateDomain(
                    serverTag, serverUri, serverToken
                )
            )
            loadScreenLiveData.postValue(VisibilityStates.GONE.visibility)
            navigationLiveData.postValue(true)
            webSocketRepository.close()
        }
    }

    fun getLoadingLiveData() = loadScreenLiveData
    fun getNavigationLiveData() = navigationLiveData

}