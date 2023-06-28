package com.sogya.data.mappers.state

import com.sogya.data.models.StateData
import com.sogya.domain.models.StateDomain

class ListOfStatesMapper(
    private val listStateData: List<StateData>
) {
    fun toDomainList(): List<StateDomain> {
        val list = arrayListOf<StateDomain>()
        listStateData.forEach {
            list.add(StatesMapper(it).toStateDomain())
        }
        return list
    }
}