package com.srdroid.memedb.presentation.meme_search

import com.srdroid.memedb.domain.model.MemeModel

data class MemeSearchState(
    val isLoading: Boolean = false,
    val data: List<MemeModel>? = null,
    val error: String = ""
)