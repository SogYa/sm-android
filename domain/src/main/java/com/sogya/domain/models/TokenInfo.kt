package com.sogya.domain.models

data class TokenInfo(
    val access_token: String,
    val expires_in: Double,
    val refresh_token: String,
    val token_type: String
)
