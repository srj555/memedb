package com.srdroid.memedb.presentation.mapper

import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.presentation.model.MemeUIModel
import javax.inject.Inject

class MemeMapper @Inject constructor() : Mapper<MemeUIModel, MemeModel> {
    override fun mapToView(input: MemeModel): MemeUIModel {
        return MemeUIModel(
            input.id,
            input.name,
            input.image,
            input.width,
            input.height,
            input.imageAspectRation
        )
    }
}