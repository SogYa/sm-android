package com.sogya.data.mappers.state

import com.sogya.data.mappers.state.attributes.AttributeMapper
import com.sogya.data.models.State
import com.sogya.domain.models.StateDomain

class StatesMapper(
    private val state: State
) {
    fun toStateDomain(): StateDomain = StateDomain(
        state.entityId,
        state.state,
        state.lastUpdated,
        state.lastChanged,
        AttributeMapper(state.attributes).toAttributeDomain()
    )
}