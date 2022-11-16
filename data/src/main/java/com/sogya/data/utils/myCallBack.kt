package com.sogya.data.utils

interface myCallBack<T> {
    fun data(t:T)
    fun error()
}