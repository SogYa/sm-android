package com.sogya.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.sogya.data.models.ServerState
import com.sogya.data.models.State

data class StateAndServer(
    @Embedded val serverState: ServerState,
    @Relation(
        parentColumn = "serverUri",
        entityColumn = "stateId"
    )
    val states: List<State>
)
