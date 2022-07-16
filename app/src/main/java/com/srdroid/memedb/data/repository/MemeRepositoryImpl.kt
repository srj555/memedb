package com.srdroid.memedb.data.repository

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.core.Result
import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.error.ErrorHandler
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.domain.repository.MemeRepository

class MemeRepositoryImpl(
    private val memeAPI: MemeAPI,
    private val errorHandler: ErrorHandler
) :
    MemeRepository {

    override suspend fun getMemes(): Result<MemeDTO> {
        return try {
            Result.Success(data = memeAPI.getMemes())
        } catch (t: Throwable) {
            Result.Error(
                message = t.localizedMessage ?: AppConstants.UNKNOWN_ERROR,
                errorEntity = errorHandler.getError(t)
            )
        }
    }
}