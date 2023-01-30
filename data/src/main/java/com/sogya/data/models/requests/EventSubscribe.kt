package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class EventSubscribe(
    @SerializedName("id")
    val id: Int = 18,
    @SerializedName("type")
    val type: String = "subscribe_events",
    @SerializedName("event_type")
    val eventType: String = "state_changed"
)