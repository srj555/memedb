package com.srdroid.memedb.presentation.meme_details

import com.srdroid.memedb.domain.model.MemeModel

data class MemeDetailsState(
    val isLoading: Boolean = false,
    val data: MemeModel? = null,
    val error: String = ""
)