package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class LongLivedRequest(
    @SerializedName("id")
    val id: Int = 11,
    @SerializedName("type")
    val type: String = "auth/long_lived_access_token",
    @SerializedName("client_name")
    val title: String = "SmartRevolutionAndroid_" + System.currentTimeMillis(),
    @SerializedName("lifespan")
    val lifespan: Int = 3650
)