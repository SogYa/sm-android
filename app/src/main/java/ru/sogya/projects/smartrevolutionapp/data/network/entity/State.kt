package ru.sogya.projects.smartrevolutionapp.data.network.entity

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

    @SerializedName("checksum")
    val checksum: String,

    @SerializedName("displayOrder")
    val displayOrder: Int
)