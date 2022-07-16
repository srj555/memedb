package com.srdroid.memedb.presentation.model

import com.srdroid.memedb.domain.errorhandler.ErrorType

data class ErrorUIModel(
   val message: String,
   @ErrorType val type:Int,
   val data:Any? = null
)