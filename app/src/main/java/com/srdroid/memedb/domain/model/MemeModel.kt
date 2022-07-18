package com.srdroid.memedb.domain.model


data class MemeModel(
    val id: String,
    val name: String,
    val image: String,
    val width: Int,
    val height: Int,
    val imageAspectRation: String,
)