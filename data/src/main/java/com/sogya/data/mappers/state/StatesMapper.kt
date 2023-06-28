package com.sogya.data.mappers.state

import com.sogya.data.mappers.state.attributes.AttributeMapper
import com.sogya.data.models.StateData
import com.sogya.domain.models.StateDomain

class StatesMapper(
    private val stateData: StateData
) {
    fun toStateDomain(): StateDomain = StateDomain(
        stateData.entityId,
        stateData.state,
        stateData.lastUpdated,
        stateData.lastChanged,
        AttributeMapper(stateData.attributes).toAttributeDomain(),
        stateData.ownerId,
        stateData.groupId
    )
}