package com.srdroid.memedb.domain.repository

import com.srdroid.memedb.core.Result
import com.srdroid.memedb.data.model.MemeDTO

interface MemeDetailsRepository {
    suspend fun getMemeDetails(id: String): Result<MemeDTO>
}