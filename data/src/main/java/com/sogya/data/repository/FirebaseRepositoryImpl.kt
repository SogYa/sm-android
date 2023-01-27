package com.sogya.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.models.UserDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack
import java.util.*

class FirebaseRepositoryImpl : FirebaseRepository {
    private val firebaseAuthInstance = FirebaseAuth.getInstance()
    private val firebaseDatabaseInstance = FirebaseDatabase.getInstance()
    private val firebaseReference = firebaseDatabaseInstance.reference

    override fun createUser(email: String, password: String, myCallBack: MyCallBack<String>) {
        firebaseAuthInstance.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data(it.result.user?.uid.toString())
            } else {
                if (it.exception != null) {
                    myCallBack.data(it.exception?.message.toString())
                } else {
                    myCallBack.error()
                }
            }
        }
    }

    override fun logInUser(email: String, password: String, myCallBack: MyCallBack<String>) {
        firebaseAuthInstance.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data(it.result.user?.uid.toString())
            } else {
                if (it.exception != null) {
                    myCallBack.data(it.exception?.message.toString())
                } else {
                    myCallBack.error()
                }
            }
        }
    }

    override fun logOut() {
        firebaseAuthInstance.signOut()
    }

    override fun sendEmailVerification(myCallBack: MyCallBack<String>) {
        firebaseAuthInstance.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data("")
            } else {
                if (it.exception != null) {
                    myCallBack.data(it.exception?.message.toString())
                }
            }
        }
    }

    override fun writeUser(name: String, email: String, myCallBack: MyCallBack<String>) {
        val uid = firebaseAuthInstance.currentUser?.uid.toString()
        val user = UserDomain(uid, email, name, listOf())
        firebaseReference.child("User").child(uid).setValue(user)
            .addOnCompleteListener {
                if (it.exception != null)
                    myCallBack.error(it.exception?.message.toString())
            }
    }

    override fun readUser(myCallBack: MyCallBack<UserDomain?>) {
        firebaseReference.child("User")
            .child(firebaseAuthInstance.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user: UserDomain? = snapshot.getValue(UserDomain::class.java)
                    myCallBack.data(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    myCallBack.error(error.message)
                }
            })
    }

    override fun createTicket(
        ticketDevice: String,
        ticketZone: String,
        ticketDesc: String?,
        ticketDate: String,
        myCallBack: MyCallBack<Boolean>
    ) {
        val ticketId = UUID.randomUUID().toString()
        val ticketStatus = "Created"
        val userId = firebaseAuthInstance.currentUser?.uid.toString()
        val ticket =
            TicketDomain(
                ticketId = ticketId,
                userId = userId,
                ticketDate = ticketDate,
                ticketDevice = ticketDevice,
                ticketZone = ticketZone,
                ticketDesc = ticketDesc,
                ticketStatus = ticketStatus
            )
        firebaseReference.child("Tickets").setValue(ticket)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    myCallBack.data(true)
                }
                if (it.exception != null)
                    myCallBack.error(it.exception?.message.toString())
            }
    }

    override fun readAllTickets(myCallBack: MyCallBack<TicketDomain>) {
        val userId = firebaseAuthInstance.currentUser?.uid.toString()
        firebaseReference.child("Tickets").orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ticket = snapshot.getValue(TicketDomain::class.java)
                    if (ticket != null) {
                        myCallBack.data(ticket)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    myCallBack.error(error.message)
                }
            })
    }

    override fun readTicketByID(ticketId: String, myCallBack: MyCallBack<TicketDomain>) {
        TODO("Not yet implemented")
    }

    override fun deleteTicketById(
        ticketId: String,
        myCallBack: MyCallBack<String>
    ) {
        TODO("Not yet implemented")
    }
}
