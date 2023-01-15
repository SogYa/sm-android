package com.sogya.domain.repository

import com.sogya.domain.utils.MyCallBack

interface FirebaseRepository {
    fun createUser(email:String, password: String,myCallBack: MyCallBack<String>)

    fun logInUser(email: String,password: String, myCallBack: MyCallBack<String>)

    fun logOut()
}