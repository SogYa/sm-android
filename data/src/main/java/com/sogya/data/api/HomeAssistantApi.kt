package com.sogya.data.api


import com.sogya.domain.models.Message
import com.sogya.data.models.State
import com.sogya.domain.models.TokenInfo
import io.reactivex.Single
import okhttp3.HttpUrl
import retrofit2.http.*

interface HomeAssistantApi {

    @GET("/api/")
    fun getApiMessage(@Header("Authorization") token: String): Single<Message>

    @GET("/api/states")
    fun getApiStates(@Header("Authorization") token: String): Single<List<State>>

    @FormUrlEncoded
    @POST("/auth/token")
     fun getApiToken(
        @Field("grant_type") grandType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String
    ): Single<TokenInfo>
}