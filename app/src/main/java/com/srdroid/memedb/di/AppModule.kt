package com.srdroid.memedb.di

import com.srdroid.memedb.domain.errorhandler.ErrorHandler
import com.srdroid.memedb.domain.errorhandler.GeneralErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }
}