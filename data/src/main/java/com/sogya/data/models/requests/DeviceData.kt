package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName
import com.sogya.domain.utils.Constants

data class DeviceData(
    @SerializedName("device_id")
    val deviceId: String = "SmartRevolutionAndroid_" + System.currentTimeMillis(),
    @SerializedName("app_id")
    val appId: String = "SmartRevolutionApp",
    @SerializedName("app_name")
    val appName: String = "Smart Revolution",
    @SerializedName("app_version")
    val appVersion: String = Constants.APP_VERSION,
    @SerializedName("device_name")
    var deviceName: String,
    @SerializedName("manufacturer")
    var manufacturer: String,
    @SerializedName("model")
    var model: String,
    @SerializedName("os_name")
    val operationSystemName: String = "Android",
    @SerializedName("os_version")
    var operationSystemVersion: String,
    @SerializedName("supports_encryption")
    val support: String = ""
)