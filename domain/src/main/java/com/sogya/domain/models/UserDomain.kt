package com.sogya.domain.models

data class UserDomain(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val serverUriList: List<String>? = listOf()
)