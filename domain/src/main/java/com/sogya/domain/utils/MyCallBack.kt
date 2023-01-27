package com.sogya.domain.utils

interface MyCallBack<T> {
    fun data(t:T){}
    fun error(){}
    fun error(error: String){}
}