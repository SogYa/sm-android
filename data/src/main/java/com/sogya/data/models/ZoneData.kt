package com.sogya.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.ZoneDomain

@Entity(tableName = "zones")
data class ZoneData(
    @PrimaryKey
    @SerializedName("zone_id")
    val id: String,
    @SerializedName("zone_owner_id")
    val ownerId:String,
    @SerializedName("zone_title")
    val title: String? = null,
    @SerializedName("coordinate_latitude")
    val latitude: Double? = null,
    @SerializedName("coordinate_longitude")
    val longitude: Double? = null
) {
    fun toDomain() = ZoneDomain(id, ownerId,title, latitude, longitude)
}