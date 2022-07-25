package com.srdroid.memedb.di

import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.repository.MemeDetailsRepositoryImpl
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object MemeDetailsModule {
    @Provides
    fun provideMemeDetailsRepository(memeAPI: MemeAPI): MemeDetailsRepository {
        return MemeDetailsRepositoryImpl(memeAPI)
    }
}