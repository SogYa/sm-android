package com.sogya.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servers")
data class ServerState(
    @PrimaryKey(autoGenerate = true)
    val serverId: Int,
    val serverName: String,
    val serverUri: String,
    val serverToken: String
)