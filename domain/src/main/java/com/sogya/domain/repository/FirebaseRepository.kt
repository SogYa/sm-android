package com.sogya.domain.repository

import com.sogya.domain.models.UserDomain
import com.sogya.domain.utils.MyCallBack

interface FirebaseRepository {
    fun createUser(email: String, password: String, myCallBack: MyCallBack<String>)

    fun logInUser(email: String, password: String, myCallBack: MyCallBack<String>)

    fun logOut()

    fun sendEmailVerification(myCallBack: MyCallBack<String>)

    fun writeUser(name: String, email: String, myCallBack: MyCallBack<String>)

    fun readUser(myCallBack: MyCallBack<UserDomain?>)
}