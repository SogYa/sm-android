package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class EventSubscribe(
    @SerializedName("id")
    var id: Int,
    @SerializedName("type")
    val type: String = "subscribe_events",
    @SerializedName("event_type")
    val eventType: String = "state_changed"
)