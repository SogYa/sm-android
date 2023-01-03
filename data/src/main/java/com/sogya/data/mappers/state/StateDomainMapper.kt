package com.sogya.data.mappers.state

import com.sogya.data.mappers.state.attributes.AttributeDomainMapper
import com.sogya.data.models.State
import com.sogya.domain.models.StateDomain

class StateDomainMapper (
    private val state: StateDomain
) {
    fun toStateData(): State = State(
        state.entityId,
        state.state,
        state.lastUpdated,
        state.lastChanged,
        AttributeDomainMapper(state.attributesDomain).toAttributeData()
    )
}