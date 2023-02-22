package com.sogya.data.models

import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.IntegrationResponseDomain

data class IntegrationResponseData(
    @SerializedName("cloudhook_url") val cloudHookUrl: String,
    @SerializedName("remote_ui_url") val remoteUiUrl: String,
    @SerializedName("secret") val token: String,
    @SerializedName("webhook_id") val webhookId: String
) {
    fun toDomain() = IntegrationResponseDomain(cloudHookUrl, remoteUiUrl, token, webhookId)
}
