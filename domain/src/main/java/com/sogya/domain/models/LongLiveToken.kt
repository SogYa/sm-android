package com.sogya.domain.models

data class LongLiveToken(
    val id: Int,
    val type: String,
    val client_name: String,
    val result: String
)
