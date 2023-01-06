package com.sogya.domain.models

data class StateGroupDomain(
    val ownerId: String,
    var groupTag: String = "",
    var groupDesc: String = ""
)