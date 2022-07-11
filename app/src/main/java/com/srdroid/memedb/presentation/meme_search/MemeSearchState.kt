package com.srdroid.memedb.presentation.meme_search

import com.srdroid.memedb.presentation.model.ErrorUIModel
import com.srdroid.memedb.presentation.model.MemeUIModel

data class MemeSearchState(
    val isLoading: Boolean = false,
    val data: List<MemeUIModel>? = null,
    val error: ErrorUIModel? = null
)

