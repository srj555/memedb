package com.srdroid.memedb.presentation.mapper

import android.content.Context
import com.srdroid.memedb.R
import com.srdroid.memedb.core.Mapper
import com.srdroid.memedb.data.error.ErrorEntity
import com.srdroid.memedb.domain.error.ErrorType
import com.srdroid.memedb.presentation.model.ErrorUIModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorViewMapper @Inject constructor(@ApplicationContext private val context: Context) :
    Mapper<ErrorUIModel, ErrorEntity?> {

    override fun mapToOut(input: ErrorEntity?): ErrorUIModel {
        return ErrorUIModel(
            message = getMessage(input),
            type = getErrorType(input)
        )
    }

    private fun getMessage(errorEntity: ErrorEntity?): String {
        return when (errorEntity) {
            ErrorEntity.Network -> context.getString(R.string.network_error)
            ErrorEntity.Unknown -> context.getString(R.string.unknown_error)
            ErrorEntity.ServiceUnavailable -> context.getString(R.string.service_error)
            else -> context.getString(R.string.unknown_error)
        }
    }

    private fun getErrorType(errorEntity: ErrorEntity?): Int {
        return when (errorEntity) {
            ErrorEntity.Network -> ErrorType.TOAST
            ErrorEntity.Unknown -> ErrorType.TOAST
            ErrorEntity.ServiceUnavailable -> ErrorType.TOAST
            else -> ErrorType.TOAST
        }
    }
}