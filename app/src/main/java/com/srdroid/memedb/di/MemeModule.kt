package com.srdroid.memedb.di

import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.repository.MemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MemeModule {

    @Provides
    @Singleton
    fun provideMemeAPI(retrofit: Retrofit): MemeAPI {
        return retrofit.create(MemeAPI::class.java)
    }

    @Provides
    fun provideMemeSearchRepository(memeAPI: MemeAPI): MemeRepository {
        return MemeRepositoryImpl(memeAPI)
    }
}