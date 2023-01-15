package com.sogya.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.sogya.domain.repository.FirebaseRepository
import com.sogya.domain.utils.MyCallBack

class FirebaseRepositoryImpl : FirebaseRepository {
    private val firebaseInstance = FirebaseAuth.getInstance()

    override fun createUser(email: String, password: String, myCallBack: MyCallBack<String>) {
        firebaseInstance.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data(it.result.user?.uid.toString())
            } else if (it.isCanceled)
                myCallBack.error()
        }
    }

    override fun logInUser(email: String, password: String, myCallBack: MyCallBack<String>) {
        firebaseInstance.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data(it.result.user?.uid.toString())
            } else if (it.isCanceled)
                myCallBack.error()
        }
    }

    override fun logOut() {
        firebaseInstance.signOut()
    }

    override fun sendEmailVerification(myCallBack: MyCallBack<Boolean>) {
        firebaseInstance.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                myCallBack.data(true)
            } else if (it.isCanceled)
                myCallBack.error()
        }
    }
}
