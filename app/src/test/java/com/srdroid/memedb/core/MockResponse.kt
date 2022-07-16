package com.srdroid.memedb.core

import com.srdroid.memedb.data.error.ErrorEntity
import com.srdroid.memedb.data.model.Data
import com.srdroid.memedb.data.model.Meme
import com.srdroid.memedb.data.model.MemeDTO
import com.srdroid.memedb.domain.mappers.MemeModelMapper
import com.srdroid.memedb.domain.model.MemeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

const val ID = "1"

object MockResponse {

    fun getMemesModel(): Result<MemeDTO> {
        val memesModel = Meme(
            box_count = 1,
            height = 2,
            id = "1",
            name = "drake",
            url = "a",
            width = 2
        )
        val listMemesModel: List<Meme> = listOf(memesModel)
        return Result.Success(data = MemeDTO(data = Data(listMemesModel), success = true))
    }

    fun getMemesModelFailure(): Result<MemeDTO> {
        return Result.Error(message = "Network Error", errorEntity = ErrorEntity.Network)
    }

    fun getResourceData(): Flow<Result<List<MemeModel>>> = channelFlow {
        var domainData = listOf<MemeModel>()
        val mapper = MemeModelMapper()
        val data =
            getMemesModel()
        domainData =
            if (data.data?.success == true) data.data?.data?.memes?.map { mapper.mapToOut(it) }
                ?: domainData else emptyList()
        send(Result.Success(data = domainData))

    }


    fun getDataFailureMock(): Flow<Result<List<MemeModel>>> = channelFlow {
        val domainData = listOf<MemeModel>()
        send(
            Result.Error(
                message = "An Unknown error occurred",
                data = domainData,
                errorEntity = ErrorEntity.Network
            )
        )
    }

    fun getDataFailureUnknown(): Flow<Result<List<MemeModel>>> = channelFlow {
        val domainData = listOf<MemeModel>()
        send(
            Result.Loading(
                data = domainData,
            )
        )
    }
}