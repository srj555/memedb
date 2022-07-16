package com.srdroid.memedb.domain.use_case

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.error.ErrorHandler
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetMemeDetailsUseCase @Inject constructor(
    private val repository: MemeDetailsRepository,
    private val mapper:MemeModelMapper,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(id: String): Flow<Resource<List<MemeModel>>> = channelFlow {
        try {
            val data = repository.getMemeDetails(id)
            val domainData =
                if (data.success) data.data.memes.map { mapper.mapToOut(it) } else emptyList()
            send(Resource.Success(data = domainData))
        } catch (t: Throwable) {
            send(
                Resource.Error(
                    message = t.localizedMessage ?: AppConstants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(t)
                )
            )
        }
    }


}