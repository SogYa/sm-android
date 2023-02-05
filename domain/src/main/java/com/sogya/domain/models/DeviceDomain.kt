package com.sogya.domain.models

import com.sogya.domain.utils.Constants

data class DeviceDomain(
    val deviceId: String = "SmartRevolutionAndroid_" + System.currentTimeMillis(),
    val appId: String = "SmartRevolutionApp",
    val appName: String = "Smart Revolution",
    val appVersion: String = Constants.APP_VERSION,
    var deviceName: String,
    var manufacturer: String,
    var model: String,
    val operationSystemName: String = "Android",
    var operationSystemVersion: String,
    val support: String = ""
)