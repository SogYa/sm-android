package com.sogya.data.network.api


import com.sogya.data.models.IntegrationResponseData
import com.sogya.data.models.MessageData
import com.sogya.data.models.StateData
import com.sogya.data.models.requests.IntegrationRequestData
import com.sogya.domain.models.TokenInfo
import retrofit2.http.*

interface HomeAssistantApi {
    @GET("/api/")
    suspend fun getApiMessage(@Header("Authorization") token: String): MessageData

    @GET("/api/states")
    suspend fun getApiStates(@Header("Authorization") token: String): List<StateData>

    @GET("/api/history/period/{timestamp}?filter_entity_id={entityId}")
    suspend fun getStateHistory(
        @Header("Authorization") token: String,
        @Path("timestamp") timestamp: String,
        @Path("entityId") entityId: String
    ): List<StateData>


    @FormUrlEncoded
    @POST("/auth/token")
    suspend fun getApiToken(
        @Field("grant_type") grandType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String
    ): TokenInfo

    @POST("/api/mobile_app/registrations")
    suspend fun registrationMobileAppIntegration(
        @Header("Authorization") token: String, @Body device: IntegrationRequestData
    ): IntegrationResponseData

    @GET("/api/states/{entityId}")
    suspend fun getStateById(
        @Header("Authorization") token: String, @Path("entityId") entityId: String
    ): StateData
}