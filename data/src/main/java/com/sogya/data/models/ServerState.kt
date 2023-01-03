package com.sogya.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "servers")
data class ServerState(
    @PrimaryKey
    @SerializedName("serverUri")
    val serverUri: String = "",
    val serverName: String = "",
    val serverToken: String = ""
)