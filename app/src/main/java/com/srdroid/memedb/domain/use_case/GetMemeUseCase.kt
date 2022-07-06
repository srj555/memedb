package com.srdroid.memedb.domain.use_case

import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.data.model.toDomainMeme
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetMemeUseCase @Inject constructor(private val repository: MemeRepository) {

    operator fun invoke(): Flow<Resource<List<MemeModel>>> = channelFlow {
        var domainData = listOf<MemeModel>()
        try {
            val data =
                repository.getMemes()
            domainData =
                if (data.success) data.data.memes.map { it.toDomainMeme() } else domainData
            send(Resource.Success(data = domainData))
        } catch (e: HttpException) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: "An Unknown error occurred",
                    data = domainData
                )
            )
        } catch (e: IOException) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: "Check Connectivity",
                    data = domainData
                )
            )
        } catch (e: Exception) {
            send(
                Resource.Error(
                    message = e.localizedMessage ?: "An Unknown error occurred",
                    data = domainData
                )
            )
        }
    }


}