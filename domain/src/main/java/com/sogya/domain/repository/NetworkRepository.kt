package com.sogya.domain.repository

import io.reactivex.Single
import com.sogya.domain.models.Message

interface NetworkRepository {

    fun getMessage(baseUri:String,token:String): Single<Message>
}