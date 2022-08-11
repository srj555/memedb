package com.srdroid.memedb.presentation.model

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: ErrorUIModel? = null
)
