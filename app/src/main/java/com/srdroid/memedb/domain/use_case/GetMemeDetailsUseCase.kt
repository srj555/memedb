package com.srdroid.memedb.domain.use_case

import com.srdroid.memedb.core.AppConstants
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.data.model.toDomainMeme
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.repository.MemeDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMemeDetailsUseCase @Inject constructor(private val repository: MemeDetailsRepository) {

    operator fun invoke(id: String): Flow<Resource<List<MemeModel>>> = flow {
        var domainData = listOf<MemeModel>()
        try {
            emit(Resource.Loading(data = domainData))
            val data = repository.getMemeDetails(id)
            domainData =
                if (data.success) data.data.memes.map { it.toDomainMeme() } else emptyList()
            emit(Resource.Success(data = domainData))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.UNKNOWN_ERROR,
                    data = domainData
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.CONNECTIVITY_ERROR,
                    data = domainData
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: AppConstants.CONNECTIVITY_ERROR,
                    data = domainData
                )
            )
        }
    }


}