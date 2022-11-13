package com.sogya.data.api


import com.sogya.domain.models.Message
import com.sogya.data.models.State
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeAssistantApi {

    @GET("/api/")
    fun getApiMessage(@Header("Authorization") token: String): Single<Message>

    @GET("/api/states")
    fun getApiStates(@Header("Authorization") token: String): Single<List<State>>

}