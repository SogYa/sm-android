package ru.sogya.projects.smartrevolutionapp.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sogya.data.mappers.state.attributes.AttributeMapper
import com.sogya.data.models.State
import com.sogya.data.models.requests.AuthMessage
import com.sogya.data.models.requests.EventSubscribe
import com.sogya.domain.models.StateDomain
import com.sogya.domain.repository.MessageListener
import com.sogya.domain.usecases.databaseusecase.states.CheckStateExistUSeCase
import com.sogya.domain.usecases.databaseusecase.states.GetStateByIdUseCase
import com.sogya.domain.usecases.databaseusecase.states.UpdateStateUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.usecases.websocketus.InitUseCase
import com.sogya.domain.usecases.websocketus.ReconnectUseCase
import com.sogya.domain.usecases.websocketus.SendMessageUseCase
import com.sogya.domain.utils.Constants
import org.json.JSONObject
import ru.sogya.projects.smartrevolutionapp.app.App



class EventWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), MessageListener {
    private val repository = App.getWebSocketRepository()
    private val iniUseCase = InitUseCase(repository)
    private val roomRepository = App.getRoom()
    private val sharedPreferencesRepository  =App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val updateStateUseCase = UpdateStateUseCase(repository = roomRepository)
    private val getStateById = GetStateByIdUseCase(roomRepository)
    private val reconnectUseCase = ReconnectUseCase(repository)
    private val sendMessageUseCase = SendMessageUseCase(repository)
    private val checkStateExistUSeCase = CheckStateExistUSeCase(roomRepository)
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
            sendMessageUseCase.invoke(EventSubscribe())
        } else if (result.get("type") == "result") {
            if (result.get("success").toString() == "false") {
                Log.d("Error", result.get("error").toString())
            }
            Log.d("EventSubscription", result.get("success").toString())
        } else if (result.get("type") == "event") {
            val newStateJson =
                result.getJSONObject("event").getJSONObject("data").getJSONObject("new_state")
                    .toString()
            val mJson = JsonParser.parseString(newStateJson)

            val newStateData = Gson().fromJson(mJson, State::class.java)
            Log.d("NewState", "${newStateData.entityId} changed with state${newStateData.state}")
            if (checkStateExistUSeCase.invoke(newStateData.entityId)) {
                val oldState = getStateById.invoke(newStateData.entityId)

                val newState = StateDomain(
                    newStateData.entityId,
                    newStateData.state,
                    newStateData.lastUpdated,
                    newStateData.lastChanged,
                    AttributeMapper(newStateData.attributes).toAttributeDomain(),
                    oldState.ownerId,
                    oldState.groupId
                )
                updateStateUseCase.invoke(newState)
            }
        }
    }
}