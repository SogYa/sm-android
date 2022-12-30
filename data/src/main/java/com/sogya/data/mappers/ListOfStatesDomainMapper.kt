package com.sogya.data.mappers

import com.sogya.data.models.State
import com.sogya.domain.models.StateDomain

class ListOfStatesDomainMapper(
    private val listState: List<StateDomain>
) {
    fun toDataList(): List<State> {
        val list = arrayListOf<State>()
        listState.forEach {
            list.add(StateDomainMapper(it).toStateData())
        }
        return list
    }
}