package com.sogya.data.models.requests

import com.google.gson.annotations.SerializedName

data class NotificationSubscribe(
    @SerializedName("id")
    var id: Int,
    @SerializedName("type")
    val type: String = "mobile_app/push_notification_channel",
    @SerializedName("webhook_id")
    val webHookId:String
)