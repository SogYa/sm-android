package com.sogya.data.models

data class UserData(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val serverUriList: List<String>? = listOf()
)