package com.sogya.domain.models

data class StateGroupDomain(
    val groupId: Int,
    val ownerId: String,
    var groupTag: String = "",
    var groupDesc: String = "",
    val stateList: List<StateDomain> = listOf()
)