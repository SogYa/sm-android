package com.sogya.domain.models

data class StateGroupDomain(
    var groupId: Int = -1,
    val ownerId: String,
    var groupTag: String = "",
    var groupDesc: String = ""
)