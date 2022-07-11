package com.srdroid.memedb.presentation.meme_details

import com.srdroid.memedb.presentation.model.MemeUIModel

data class MemeDetailsState(
    val isLoading: Boolean = false,
    val data: MemeUIModel? = null,
    val error: String = ""
)