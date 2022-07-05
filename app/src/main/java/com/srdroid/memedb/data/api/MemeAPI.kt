package com.srdroid.memedb.data.api

import com.srdroid.memedb.data.model.MemeDTO
import retrofit2.http.GET

interface MemeAPI {
    @GET("get_memes")
    suspend fun getMemes(): MemeDTO
}