package ru.sogya.projects.smartrevolutionapp.data.network.entity

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    val message:String
)
