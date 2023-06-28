package com.sogya.data.models

import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.IntegrationResponseDomain

data class IntegrationResponseData(
    @SerializedName("cloudhook_url") override val cloudHookUrl: String,
    @SerializedName("remote_ui_url") override val remoteUiUrl: String,
    @SerializedName("secret") override val token: String,
    @SerializedName("webhook_id") override val webhookId: String
) : IntegrationResponseDomain
