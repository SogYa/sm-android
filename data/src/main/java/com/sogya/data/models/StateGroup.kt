package com.sogya.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StateGroup(
    @PrimaryKey(autoGenerate = true)
    val groupId: Int =0,
    val ownerId: String,
    var groupTag: String = "",
    var groupDesc: String = ""
)
