package com.sogya.domain.models

data class StateDomain(
    val entityId: String,
    val state: String,
    val lastUpdated: String,
    val lastChanged: String,
    val attributesDomain: AttributesDomain,
    var ownerId: String,
    var groupId: Int
) {
    constructor(entityId: String, state: String, ownerId: String) : this(
        entityId, state, "", "",
        AttributesDomain(), ownerId, -1
    )
//    fun isPersone(): Boolean {
//        return (state.contains("person."))
//    }
}