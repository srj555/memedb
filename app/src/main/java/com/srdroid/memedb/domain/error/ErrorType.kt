package com.srdroid.memedb.domain.error

import androidx.annotation.IntDef
import com.srdroid.memedb.domain.error.ErrorType.Companion.ALERT
import com.srdroid.memedb.domain.error.ErrorType.Companion.DIALOG
import com.srdroid.memedb.domain.error.ErrorType.Companion.SNACK
import com.srdroid.memedb.domain.error.ErrorType.Companion.TOAST

@IntDef(SNACK, TOAST, ALERT, DIALOG)
annotation class ErrorType {
    companion object {
        const val SNACK = 1
        const val TOAST = 2
        const val ALERT = 3
        const val DIALOG = 4
    }
}