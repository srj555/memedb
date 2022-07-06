package com.srdroid.memedb.hilt

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.data.api.MemeAPI
import com.srdroid.memedb.data.repository.MemeDetailsRepositoryImpl
import com.srdroid.memedb.data.repository.MemeRepositoryImpl
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import com.srdroid.memedb.domain.repository.MemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModule {


    @Provides
    @Singleton
    fun provideMemeSearchAPI(): MemeAPI {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MemeAPI::class.java)
    }


    @Provides
    fun provideMemeSearchRepository(memeAPI: MemeAPI): MemeRepository {
        return MemeRepositoryImpl(memeAPI)
    }


    @Provides
    fun provideMemeDetails(searchMemeAPI: MemeAPI): MemeDetailsRepository {
        return MemeDetailsRepositoryImpl(searchMemeAPI)
    }


}