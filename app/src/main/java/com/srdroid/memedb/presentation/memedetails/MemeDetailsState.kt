package com.srdroid.memedb.presentation.memedetails

import com.srdroid.memedb.presentation.model.ErrorUIModel
import com.srdroid.memedb.presentation.model.MemeItemUIState

data class MemeDetailsState(
    val isLoading: Boolean = false,
    val data: MemeItemUIState? = null,
    val error: ErrorUIModel? = null
)