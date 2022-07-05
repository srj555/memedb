package com.srdroid.memedb.data.repository

import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.domain.repository.MemeRepository

class MemeRepositoryImpl(private val memeAPI: MemeAPI) :
    MemeRepository {

    override suspend fun getMemes(): MemeDTO {
        return memeAPI.getMemes()
    }
}