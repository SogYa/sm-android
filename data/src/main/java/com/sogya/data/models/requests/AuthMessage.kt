package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName


data class AuthMessage(
    @SerializedName("type")
    val type: String = "auth",
    @SerializedName("access_token")
    val token: String
)
