package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sogya.data.mappers.state.attributes.AttributeMapper
import com.sogya.data.models.StateData
import com.sogya.data.models.requests.AuthMessage
import com.sogya.data.models.requests.EventSubscribe
import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.LocalDataBaseRepository
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.repository.SharedPreferencesRepository
import com.sogya.domain.repository.WebSocketRepository
import com.sogya.domain.usecases.databaseusecase.states.CheckStateExistUseCase
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.UpdateStateUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.websockets.InitUseCase
import com.sogya.domain.usecases.websockets.ReconnectUseCase
import com.sogya.domain.usecases.websockets.SendMessageUseCase
import com.sogya.domain.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.json.JSONObject


@HiltWorker
class EventWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    webSocketRepository: WebSocketRepository,
    sharedPreferencesRepository: SharedPreferencesRepository,
    localDataBaseRepository: LocalDataBaseRepository
) :
    Worker(context, workerParams), MessageListener {
    private val iniUseCase = InitUseCase(webSocketRepository)
    private val reconnectUseCase = ReconnectUseCase(webSocketRepository)
    private val sendMessageUseCase = SendMessageUseCase(webSocketRepository)
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val updateStateUseCase = UpdateStateUseCase(localDataBaseRepository)
    private val getStateById = GetStateByIdUseCase(localDataBaseRepository)
    private val checkStateExistUSeCase = CheckStateExistUseCase(localDataBaseRepository)
    private var count = 0
    // private var notifyCount = 1

    override fun doWork(): Result {
        val url = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        iniUseCase.invoke("$url/api/websocket", this)

        return Result.success()
    }

    override fun onConnectSuccess() {
        sendMessageUseCase.invoke(
            AuthMessage(
                token = getStringPrefsUseCase.invoke(Constants.AUTH_TOKEN)
            )
        )
    }

    override fun onConnectFailed() {
        reconnectUseCase.invoke()
    }

    override fun onClose() {
        Log.d("WebSocketMessage", "connection closed")
    }

    override fun onMessage(text: String?) {
        val result = JSONObject(text.toString())
        if (result.get("type") == "auth_ok") {
            ++count
            // ++notifyCount
            //val webHookId = getStringPrefsUseCase.invoke(Constants.INTEGRATION_WEB_HOOK)
            sendMessageUseCase.invoke(EventSubscribe(count))
            //sendMessageUseCase.invoke(NotificationSubscribe(notifyCount, webHookId = webHookId))
        } else if (result.get("type") == "result") {
            if (result.get("success").toString() == "false") {
                Log.d("Error", result.get("error").toString())
            }
            Log.d("EventSubscription", result.get("success").toString())
        } else if (result.get("type") == "event") {
            if (!result.getJSONObject("event").isNull("message")) {
                Log.d("Notify", result.toString())
            } else {
                val newStateJson =
                    result.getJSONObject("event").getJSONObject("data").getJSONObject("new_state")
                        .toString()
                val mJson = JsonParser.parseString(newStateJson)

                val newStateDataData = Gson().fromJson(mJson, StateData::class.java)
                Log.d(
                    "NewState",
                    "${newStateDataData.entityId} changed with state${newStateDataData.state}"
                )
                if (checkStateExistUSeCase.invoke(newStateDataData.entityId)) {
                    val oldState = getStateById.invoke(newStateDataData.entityId)

                    val newState = StateDomain(
                        newStateDataData.entityId,
                        newStateDataData.state,
                        newStateDataData.lastUpdated,
                        newStateDataData.lastChanged,
                        AttributeMapper(newStateDataData.attributes).toAttributeDomain(),
                        oldState.ownerId,
                        oldState.groupId
                    )
                    updateStateUseCase.invoke(newState)
                }
            }
        }
    }
}