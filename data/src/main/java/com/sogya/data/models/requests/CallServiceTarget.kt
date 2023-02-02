package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class CallServiceTarget(
    @SerializedName("entity_id")
    val entityId:String = ""
)