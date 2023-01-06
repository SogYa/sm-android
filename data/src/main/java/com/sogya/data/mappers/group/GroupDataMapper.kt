package com.sogya.data.mappers.group

import com.sogya.data.models.StateGroup
import com.sogya.domain.models.StateGroupDomain

class GroupDataMapper(
    private val groupData: StateGroup
) {
    fun toGroupDomain() = StateGroupDomain(
        groupData.ownerId,
        groupData.groupTag,
        groupData.groupDesc
    )
}