package com.sogya.data.models

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("entity_id")
    val entityId: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("last_updated")
    val lastUpdated: String,

    @SerializedName("last_changed")
    val lastChanged: String,

    @SerializedName("attributes")
    val attributes: Attribute
)