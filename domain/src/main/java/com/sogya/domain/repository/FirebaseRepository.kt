package com.sogya.domain.repository

import com.sogya.domain.models.ServerFirebaseDomain
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.models.UserDomain
import com.sogya.domain.utils.MyCallBack

interface FirebaseRepository {
    fun createUser(email: String, password: String, myCallBack: MyCallBack<String>)

    fun logInUser(email: String, password: String, myCallBack: MyCallBack<String>)

    fun logOut()

    fun sendEmailVerification(myCallBack: MyCallBack<String>)

    fun writeUser(name: String, email: String, myCallBack: MyCallBack<String>)

    fun readUser(myCallBack: MyCallBack<UserDomain?>)

    fun createTicket(
        ticketDevice: String,
        ticketZone: String,
        ticketDesc: String?,
        ticketDate: String,
        myCallBack: MyCallBack<Boolean>
    )

    fun readAllTickets(myCallBack: MyCallBack<TicketDomain>)

    fun readTicketByID(ticketId: String, myCallBack: MyCallBack<TicketDomain>)

    fun deleteTicketById(
        ticketId: String,
        myCallBack: MyCallBack<String>
    )

    fun writeServerUserLists(list: List<ServerFirebaseDomain>, myCallBack: MyCallBack<String>)
}