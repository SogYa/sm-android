package com.sogya.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.models.UserDomain
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

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
        val user = UserDomain(uid,email,name, listOf())
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

    override fun createTicket(ticketDevice: String, ticketZone: String, ticketDesc: String?,ticketDate:String) {
        TODO("Not yet implemented")
    }

    override fun readAllTickets(): LiveData<List<TicketDomain>> {
        TODO("Not yet implemented")
    }

    override fun readTicketByID(ticketId: String): LiveData<TicketDomain> {
        TODO("Not yet implemented")
    }

    override fun deleteTicketById(ticketId: String) {
        TODO("Not yet implemented")
    }
}
