package com.sogya.domain.repository

import androidx.lifecycle.LiveData

interface NetworkStatesRepository {

    fun getNetworkStatus(): LiveData<Boolean>
}