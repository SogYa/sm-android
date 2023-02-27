package com.sogya.data.mappers.zones

import com.sogya.data.models.ZoneData
import com.sogya.domain.models.ZoneDomain

class ZoneDomainMapper(
    private val zoneDomain: ZoneDomain
) {
    fun toData() =
        ZoneData(
            zoneDomain.id,
            zoneDomain.ownerId,
            zoneDomain.title,
            zoneDomain.latitude,
            zoneDomain.longitude
        )
}