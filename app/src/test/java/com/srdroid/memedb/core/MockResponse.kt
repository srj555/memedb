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

    fun getMemesModel(): MemeDTO {
        val memesModel = Meme(
            box_count = 1,
            height = 2,
            id = "1",
            name = "drake",
            url = "a",
            width = 2
        )
        val listMemesModel: List<Meme> = listOf(memesModel)
        return MemeDTO(data = Data(listMemesModel), success = true)
    }

    fun getResourceData(): Flow<Resource<List<MemeModel>>> = channelFlow {
        var domainData = listOf<MemeModel>()
        val mapper = MemeModelMapper()
        val data =
            getMemesModel()
        domainData =
            if (data.success) data.data.memes.map { mapper.mapToOut(it) } else domainData
        send(Resource.Success(data = domainData))

    }

    fun getDataFailureMock(): Flow<Resource<List<MemeModel>>> = channelFlow {
        val domainData = listOf<MemeModel>()
        send(
            Resource.Error(
                message = "An Unknown error occurred",
                data = domainData,
                errorEntity = ErrorEntity.Network
            )
        )
    }

    fun getDataFailureUnknown(): Flow<Resource<List<MemeModel>>> = channelFlow {
        val domainData = listOf<MemeModel>()
        send(
            Resource.Loading(
                data = domainData,
            )
        )
    }
}