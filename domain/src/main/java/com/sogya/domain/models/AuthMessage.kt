package com.sogya.domain.models

data class AuthMessage(
    val type: String = "auth",
    val token: String
)
