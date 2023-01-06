package com.sogya.data.mappers.group

import com.sogya.data.mappers.state.ListOfStatesMapper
import com.sogya.data.models.StateGroup
import com.sogya.domain.models.StateGroupDomain

class GroupDataMapper(
    private val groupData: StateGroup
) {
    fun toGroupDomain() = StateGroupDomain(
        groupData.groupId,
        groupData.ownerId,
        groupData.groupTag,
        groupData.groupDesc,
        ListOfStatesMapper(groupData.stateList).toDomainList()
    )
}