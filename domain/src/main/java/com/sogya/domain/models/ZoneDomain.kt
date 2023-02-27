package com.sogya.domain.models

data class ZoneDomain(
     val id: String,
     val ownerId:String,
     val title:String?,
     val latitude: Double?,
     val longitude: Double?
)
