package com.sogya.data.models

data class LongLiveAccessToken(
    val client_name: String = "SmartRevolutionAndroid_" + System.currentTimeMillis(),
    val client_icon: String = "null",
    val lifespan: Int = 3650
)