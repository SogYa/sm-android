package com.sogya.data.mappers.message

import com.sogya.data.models.MessageData
import com.sogya.domain.models.MessageDomain

class MessageMapper(
    private val messageData: MessageData
) {
    fun toMessageDomian(): MessageDomain = MessageDomain(
        messageData.message
    )
}