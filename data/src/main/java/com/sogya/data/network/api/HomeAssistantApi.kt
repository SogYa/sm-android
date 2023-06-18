package com.sogya.data.network.api


import com.sogya.data.models.IntegrationResponseData
import com.sogya.data.models.MessageData
import com.sogya.data.models.State
import com.sogya.data.models.requests.IntegrationRequestData
import com.sogya.domain.models.TokenInfo
import io.reactivex.Single
import retrofit2.http.*

interface HomeAssistantApi {
    @GET("/api/")
    fun getApiMessage(@Header("Authorization") token: String): Single<MessageData>

    @GET("/api/states")
    fun getApiStates(@Header("Authorization") token: String): Single<List<State>>

    @GET("/api/history/period/{timestamp}?filter_entity_id={entityId}")
    fun getStateHistory(
        @Header("Authorization") token: String,
        @Path("timestamp") timestamp: String,
        @Path("entityId") entityId: String
    ):Single<List<State>>


    @FormUrlEncoded
    @POST("/auth/token")
    fun getApiToken(
        @Field("grant_type") grandType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String
    ): Single<TokenInfo>

    @POST("/api/mobile_app/registrations")
    fun registrationMobileAppIntegration(
        @Header("Authorization") token: String, @Body device: IntegrationRequestData
    ): Single<IntegrationResponseData>

    @GET("/api/states/{entityId}")
    fun getStateById(
        @Header("Authorization") token: String, @Path("entityId") entityId: String
    ): Single<State>
}