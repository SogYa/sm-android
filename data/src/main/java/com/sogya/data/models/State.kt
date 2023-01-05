package com.sogya.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "states")
data class State(

    @SerializedName("entity_id") @PrimaryKey var entityId: String = "",

    @SerializedName("state") var state: String = "",

    @SerializedName("last_updated") var lastUpdated: String = "",

    @SerializedName("last_changed") var lastChanged: String = "",

    @Ignore @SerializedName("attributes") var attributes: Attributes = Attributes(),
    var ownerId: String = ""
)