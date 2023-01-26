package com.sogya.data.mappers.events

import com.sogya.data.models.TriggerEvent
import com.sogya.domain.models.TriggerEventDomain

class TriggerEventToDomain(
    private val triggerEvent: TriggerEvent
) {
    fun toDomain() = TriggerEventDomain(
        triggerEvent.platform,
        triggerEvent.entityId,
        triggerEvent.fromState,
        triggerEvent.toState
    )
}