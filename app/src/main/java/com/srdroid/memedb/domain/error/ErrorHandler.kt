package com.srdroid.memedb.domain.error

import com.srdroid.memedb.data.error.ErrorEntity

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}