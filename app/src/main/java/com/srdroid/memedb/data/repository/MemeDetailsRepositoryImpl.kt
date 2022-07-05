package com.srdroid.memedb.data.repository

import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.domain.repository.MemeDetailsRepository

class MemeDetailsRepositoryImpl(private val memeAPI: MemeAPI) :
    MemeDetailsRepository {

    override suspend fun getMemeDetails(id: String): MemeDTO {
        return memeAPI.getMemes()
    }
}