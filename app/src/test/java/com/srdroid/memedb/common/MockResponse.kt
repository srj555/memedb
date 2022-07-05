package com.srdroid.memedb.common

import com.srdroid.memedb.data.model.Data
import com.srdroid.memedb.data.model.Meme
import com.srdroid.memedb.data.model.MemeDTO

object MockResponse {

    fun getMemesModel(): MemeDTO {
        var memesModel = Meme(
            box_count = 1,
            height = 2,
            id = "1",
            name = "a",
            url = "a",
            width = 2
        )
        val listMemesModel: List<Meme> = listOf(memesModel)
        return MemeDTO(data = Data(listMemesModel), success = true)
    }
}