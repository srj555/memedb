package com.srdroid.memedb.domain.repository

import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.data.model.MemeDTO

interface MemeRepository {
    suspend fun getMemes(): Resource<MemeDTO>
}