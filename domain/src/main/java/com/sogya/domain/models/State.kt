package com.sogya.domain.models

data class State(

    val entityId: String,

    val state: String,

    val lastUpdated: String,

    val lastChanged: String,

    val checksum: String,

    val displayOrder: Int
)