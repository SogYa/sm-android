package com.sogya.data.models

data class TicketData(
    val ticketId: String = "",
    val userId: String = "",
    val ticketDate: String = "",
    val ticketDevice: String = "",
    val ticketZone: String = "",
    val ticketDesc: String? = "",
    val ticketStatus: String = ""
)