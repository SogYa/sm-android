package com.sogya.domain.models

data class TriggerEventDomain(
    val platform: String,
    val entityId: String?,
    val fromState: StateDomain,
    val toState: StateDomain
)