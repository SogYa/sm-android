package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName
import com.sogya.domain.utils.Constants

data class IntegrationRequestData(
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("app_id")
    val appId: String = "SmartRevolutionApp",
    @SerializedName("app_name")
    val appName: String = "Smart Revolution",
    @SerializedName("app_version")
    val appVersion: String = Constants.APP_VERSION,
    @SerializedName("device_name")
    val deviceName: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("os_name")
    val operationSystemName: String,
    @SerializedName("os_version")
    val operationSystemVersion: String,
    @SerializedName("supports_encryption")
    val support: Boolean = true
)