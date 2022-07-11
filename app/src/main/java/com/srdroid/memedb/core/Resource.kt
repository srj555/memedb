package com.srdroid.memedb.core

import com.srdroid.memedb.data.error.ErrorEntity


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorEntity: ErrorEntity? = null
) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null, errorEntity: ErrorEntity? = null) :
        Resource<T>(data, message, errorEntity)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}