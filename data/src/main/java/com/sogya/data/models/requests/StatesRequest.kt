package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class StatesRequest(
    @SerializedName("id")
    val id: Int = 12,
    @SerializedName("type")
    val requestType: String = "get_states"
)
