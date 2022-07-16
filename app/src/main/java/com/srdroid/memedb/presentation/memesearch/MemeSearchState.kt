package com.srdroid.memedb.presentation.memesearch

import com.srdroid.memedb.presentation.model.ErrorUIModel
import com.srdroid.memedb.presentation.model.MemeItemUIState

data class MemeSearchState(
    val isLoading: Boolean = false,
    val data: List<MemeItemUIState>? = null,
    val error: ErrorUIModel? = null
)
