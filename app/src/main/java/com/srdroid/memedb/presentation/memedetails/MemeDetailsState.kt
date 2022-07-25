package com.srdroid.memedb.presentation.memedetails

import com.srdroid.memedb.presentation.model.ErrorUIModel
import com.srdroid.memedb.presentation.model.MemeItemUIModel

data class MemeDetailsState(
    val isLoading: Boolean = false,
    val data: MemeItemUIModel? = null,
    val error: ErrorUIModel? = null
)