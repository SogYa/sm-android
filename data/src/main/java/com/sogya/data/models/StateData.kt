package com.sogya.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.StateDomain

@Entity(tableName = "states")
data class StateData(

    @SerializedName("entity_id") @PrimaryKey override var entityId: String = "",

    @SerializedName("state") override var state: String = "",

    @SerializedName("last_updated") override var lastUpdated: String = "",

    @SerializedName("last_changed") override var lastChanged: String = "",

    @Embedded @SerializedName("attributes") override var attributes: AttributesData? = null,
    override var ownerId: String = "",

    override var groupId: Int = -1
) : StateDomain