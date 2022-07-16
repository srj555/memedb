package com.srdroid.memedb.domain.use_case

import com.srdroid.memedb.core.Result
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetMemeDetailsUseCase @Inject constructor(
    private val repository: MemeDetailsRepository,
    private val mapper: MemeModelMapper,
) {

    operator fun invoke(id: String): Flow<Result<List<MemeModel>>> = channelFlow {
        val result =
            repository.getMemeDetails(id)
        when (result) {
            is com.srdroid.memedb.core.Resource.Result.Success -> {
                val domainData =
                    if (result.data?.success == true) result.data.data.memes.map {
                        mapper.mapToOut(
                            it
                        )
                    } else emptyList()
                send(Result.Success(data = domainData))
            }
            else -> send(
                Result.Error(
                    message = result.message ?: "",
                    errorEntity = result.errorEntity
                )
            )
        }
    }
}