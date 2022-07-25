package com.srdroid.memedb.presentation.memesearch

import com.srdroid.memedb.presentation.model.ErrorUIModel
import com.srdroid.memedb.presentation.model.MemeItemUIModel

data class MemeSearchState(
    val isLoading: Boolean = false,
    val data: List<MemeItemUIModel>? = null,
    val error: ErrorUIModel? = null
)

