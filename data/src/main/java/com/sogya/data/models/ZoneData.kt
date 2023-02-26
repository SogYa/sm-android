package com.sogya.data.models

import androidx.room.Entity
import com.sogya.domain.models.ZoneDomain

@Entity(tableName = "zones")
data class ZoneData(
    private val title: String,
    private val latitude: Double,
    private val longitude: Double
) {
    fun toDomain() = ZoneDomain(title, latitude, longitude)
}