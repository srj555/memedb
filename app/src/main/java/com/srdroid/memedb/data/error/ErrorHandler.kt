package com.srdroid.memedb.data.error


interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}