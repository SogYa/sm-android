package com.sogya.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "servers")
data class ServerState(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("serverId")
    val serverId: Int,
    val serverName: String,
    val serverUri: String,
    val serverToken: String
)