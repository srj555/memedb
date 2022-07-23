package com.srdroid.memedb.presentation.mapper

import com.srdroid.memedb.core.Mapper
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.presentation.model.MemeItemUIModel
import javax.inject.Inject

class MemeMapper @Inject constructor() : Mapper<MemeItemUIModel, MemeModel> {
    override fun mapToOut(input: MemeModel): MemeItemUIModel {
        return MemeItemUIModel(
            input.id,
            input.name,
            input.image,
            input.width,
            input.height,
            input.imageAspectRation
        )
    }
}