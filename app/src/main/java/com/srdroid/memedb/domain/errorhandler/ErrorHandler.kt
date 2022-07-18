package com.srdroid.memedb.domain.errorhandler

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}