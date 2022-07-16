package com.srdroid.memedb.presentation.model

data class ErrorUIModel(
    val message: String,
    @ErrorType val type: Int,
    val data: Any? = null
)