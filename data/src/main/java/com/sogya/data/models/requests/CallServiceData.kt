package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class CallServiceData(
    @SerializedName("position")
    val position: Int? = null
)

