package com.sogya.data.models

import com.google.gson.annotations.SerializedName
import com.sogya.domain.models.MessageDomain

data class MessageData(
    @SerializedName("message")
    override val message: String
) : MessageDomain
