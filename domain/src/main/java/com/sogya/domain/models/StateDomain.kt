package com.sogya.domain.models

data class StateDomain(
    val entityId: String,
    val state: String,
    val lastUpdated: String,
    val lastChanged: String,
    val attributesDomain: AttributesDomain,
    var ownerId: String
) {
//    fun isPersone(): Boolean {
//        return (state.contains("person."))
//    }
}