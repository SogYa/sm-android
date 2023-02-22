package com.sogya.domain.models

data class DeviceDataDomain(
    val deviceId:String,
    val deviceName: String,
    val manufacturer: String,
    val model: String,
    val operationSystemName: String,
    val operationSystemVersion: String,
)