package com.sogya.data.models

import com.google.gson.annotations.SerializedName

data class MessageData(
    @SerializedName("message")
    val message:String
)
