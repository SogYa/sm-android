package com.sogya.data.mappers.zones

import com.sogya.data.models.ZoneData
import com.sogya.domain.models.ZoneDomain

class ListZoneMapper(
    private val zoneDataList: List<ZoneData>
) {
    fun toDomain(): List<ZoneDomain> {
        val list = arrayListOf<ZoneDomain>()
        zoneDataList.forEach {
            list.add(it.toDomain())
        }
        return list
    }
}