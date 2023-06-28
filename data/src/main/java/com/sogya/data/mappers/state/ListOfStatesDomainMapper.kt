package com.sogya.data.mappers.state

import com.sogya.data.models.StateData
import com.sogya.domain.models.StateDomain

class ListOfStatesDomainMapper(
    private val listState: List<StateDomain>
) {
    fun toDataList(): List<StateData> {
        val list = arrayListOf<StateData>()
        listState.forEach {
            list.add(StateDomainMapper(it).toStateData())
        }
        return list
    }
}