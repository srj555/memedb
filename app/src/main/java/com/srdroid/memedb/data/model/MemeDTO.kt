package com.srdroid.memedb.data.model

import com.srdroid.memedb.data.base.DataModel
import com.srdroid.memedb.domain.model.MemeModel

data class MemeDTO(
    val `data`: Data,
    val success: Boolean
) : DataModel()

data class Data(
    val memes: List<Meme>
) : DataModel()

data class Meme(
    val box_count: Int,
    val height: Int,
    val id: String,
    val name: String,
    val url: String,
    val width: Int
) : DataModel()

fun Meme.toDomainMeme(): MemeModel {
    return MemeModel(
        id = id,
        name = name,
        image = url,
        width = width,
        height = height,
        imageAspectRation = String.format("%d:%d", width, height)
    )
}
