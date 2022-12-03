package com.sogya.data.models

import com.google.gson.annotations.SerializedName

data class LongLivedAccessToken(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("success")
    val isSuccess:Boolean,
    @SerializedName("result")
    val token: String
)