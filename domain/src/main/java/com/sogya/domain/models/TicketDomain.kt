package com.sogya.domain.models

data class TicketDomain(
    val ticketId: String = "",
    val userId: String = "",
    val ticketDate: String = "",
    val ticketDevice: String = "",
    val ticketZone: String = "",
    val ticketDesc: String? = "",
    val ticketStatus: String = ""
)