package com.sogya.data.mappers

import com.sogya.data.models.State
import com.sogya.domain.models.StateDomain

class ListOfStatesMapper(
    private val listState: List<State>
) {
    fun toDomainList(): List<StateDomain> {
        val list = arrayListOf<StateDomain>()
        listState.forEach {
            list.add(StatesMapper(it).toStateDomain())
        }
        return list
    }
}