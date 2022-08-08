package com.srdroid.memedb.domain.usecases

import com.srdroid.memedb.core.AppConstants.UNKNOWN_ERROR
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.errorhandler.ErrorHandler
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetMemeUseCase @Inject constructor(
    private val repository: MemeRepository,
    private val mapper: MemeModelMapper,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(): Flow<Resource<List<MemeModel>>> = flow {

        emit(Resource.Loading())

        val data =
            repository.getMemes()

        val domainData =
            if (data.success) data.data.memes
                .map { mapper.mapToOut(it) }
            else emptyList()

        emit(Resource.Success(data = domainData))

    }.catch { throwable ->
        emit(
            Resource.Error(
                message = throwable.localizedMessage ?: UNKNOWN_ERROR,
                errorEntity = errorHandler.getError(throwable)
            )
        )
    }
}