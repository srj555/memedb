package com.srdroid.memedb.domain.use_case

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.data.model.toDomainMeme
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMemeDetailsUseCase @Inject constructor(private val repository: MemeDetailsRepository) {

    operator fun invoke(id: String): Flow<Resource<List<MemeModel>>> = channelFlow {
        var domainData = listOf<MemeModel>()
        try {
            val data = repository.getMemeDetails(id)
            domainData =
                if (data.success) data.data.memes.map { it.toDomainMeme() } else emptyList()
            send(Resource.Success(data = domainData))
        } catch (e: HttpException) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.UNKNOWN_ERROR,
                    data = domainData
                )
            )
        } catch (e: IOException) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.CONNECTIVITY_ERROR,
                    data = domainData
                )
            )
        } catch (e: Exception) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.CONNECTIVITY_ERROR,
                    data = domainData
                )
            )
        }
    }


}