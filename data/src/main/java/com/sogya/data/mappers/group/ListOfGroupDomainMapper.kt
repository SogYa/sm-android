package com.sogya.data.mappers.group

import com.sogya.data.models.StateGroup
import com.sogya.domain.models.StateGroupDomain

class ListOfGroupDataMapper(
    private val groupList: List<StateGroup>
) {
    fun toDomainList(): List<StateGroupDomain> {
        val list = arrayListOf<StateGroupDomain>()
        groupList.forEach {
            list.add(GroupDataMapper(it).toGroupDomain())
        }
        return list
    }
}