package com.srdroid.memedb.domain.mappers

import com.srdroid.memedb.core.Mapper
import com.srdroid.memedb.data.model.Meme
import com.srdroid.memedb.domain.model.MemeModel
import javax.inject.Inject

class MemeModelMapper @Inject constructor() : Mapper<MemeModel, Meme> {

    override fun mapToOut(input: Meme): MemeModel {
        return MemeModel(
            id = input.id,
            name = input.name,
            image = input.url,
            width = input.width,
            height = input.height,
            imageAspectRation = String.format("%d:%d", input.width, input.height)
        )
    }
}