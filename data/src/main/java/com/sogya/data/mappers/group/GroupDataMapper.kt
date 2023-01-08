package com.sogya.data.mappers.group

import com.sogya.data.models.StateGroup
import com.sogya.domain.models.StateGroupDomain

class GroupDataMapper(
    private val groupData: StateGroup
) {
    fun toGroupDomain() = StateGroupDomain(
        0,
        groupData.ownerId,
        groupData.groupTag,
        groupData.groupDesc
    )
}