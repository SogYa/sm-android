package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class CallService(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String = "call_service",
    @SerializedName("domain")
    val domain:String ="",
    @SerializedName("service")
    val service: String = "",
    @SerializedName("target")
    val target: CallServiceTarget
)