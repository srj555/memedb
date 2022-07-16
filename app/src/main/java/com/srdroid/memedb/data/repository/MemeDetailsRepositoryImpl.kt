package com.srdroid.memedb.data.repository

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.data.error.ErrorHandler
import com.srdroid.memedb.domain.repository.MemeDetailsRepository

class MemeDetailsRepositoryImpl(
    private val memeAPI: MemeAPI,
    private val errorHandler: ErrorHandler
) :
    MemeDetailsRepository {

    override suspend fun getMemeDetails(id: String): Resource<MemeDTO> {
        return try {
            Resource.Success(data = memeAPI.getMemes())
        } catch (t: Throwable) {
            Resource.Error(
                message = t.localizedMessage ?: AppConstants.UNKNOWN_ERROR,
                errorEntity = errorHandler.getError(t)
            )
        }
    }
}