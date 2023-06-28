package com.sogya.data.mappers.state

import com.sogya.data.mappers.state.attributes.AttributeDomainMapper
import com.sogya.data.models.StateData
import com.sogya.domain.models.StateDomain

class StateDomainMapper (
    private val state: StateDomain
) {
    fun toStateData(): StateData = StateData(
        state.entityId,
        state.state,
        state.lastUpdated,
        state.lastChanged,
        AttributeDomainMapper(state.attributes).toAttributeData(),
        state.ownerId,
        state.groupId
    )
}