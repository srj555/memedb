package com.srdroid.memedb.domain.usecases

import com.srdroid.memedb.core.AppConstants.UNKNOWN_ERROR
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.errorhandler.ErrorHandler
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetMemeUseCase @Inject constructor(
    private val repository: MemeRepository,
    private val mapper: MemeModelMapper,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(): Flow<Resource<List<MemeModel>>> = channelFlow {
        try {
            // get meme from repo
            val data =
                repository.getMemes()
            // map to domain data model based on result
            val domainData =
                if (data.success) data.data.memes
                    .map { mapper.mapToOut(it) }
                else emptyList()
            // update result
            send(Resource.Success(data = domainData))
        } catch (t: Throwable) {
            // handle error using the error handler implementation
            send(
                Resource.Error(
                    message = t.localizedMessage ?: UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(t)
                )
            )
        }
    }
}