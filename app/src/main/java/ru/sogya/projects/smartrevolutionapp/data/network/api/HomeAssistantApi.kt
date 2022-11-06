package ru.sogya.projects.smartrevolutionapp.data.network.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.sogya.projects.smartrevolutionapp.data.network.entity.Message
import ru.sogya.projects.smartrevolutionapp.data.network.entity.State

interface HomeAssistantApi {

    @GET("/api/")
    fun getApiMessage(@Header("Authorization") token: String): Call<Message>

    @GET("/api/states")
    fun getApiStates(@Header("Authorization") token: String): Call<List<State>>

}