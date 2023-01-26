package com.sogya.data.models

import com.sogya.domain.models.StateDomain

data class TriggerEvent(
    val platform: String,
    val entityId: String?,
    val fromState: StateDomain,
    val toState: StateDomain
)