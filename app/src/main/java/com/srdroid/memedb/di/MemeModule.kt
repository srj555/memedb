package com.srdroid.memedb.di

import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.repository.MemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object MemeModule {

    @Provides
    fun provideMemeSearchRepository(memeAPI: MemeAPI): MemeRepository {
        return MemeRepositoryImpl(memeAPI)
    }
}