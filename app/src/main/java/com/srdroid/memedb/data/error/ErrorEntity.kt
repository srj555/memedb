package com.srdroid.memedb.data.error

import com.srdroid.memedb.data.base.DataModel

sealed class ErrorEntity : DataModel() {

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()
    
    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}