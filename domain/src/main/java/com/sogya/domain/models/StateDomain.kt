package com.sogya.domain.models

interface StateDomain {
    val entityId: String
    val state: String
    val lastUpdated: String
    val lastChanged: String
    val attributes: AttributesDomain?
    var ownerId: String
    var groupId: Int

    fun isZone(): Boolean {
        return (entityId.startsWith("zone."))
    }
}