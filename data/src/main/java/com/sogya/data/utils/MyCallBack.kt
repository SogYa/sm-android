package com.sogya.data.utils

interface MyCallBack<T> {
    fun data(t:T)
    fun error()
}