package com.srdroid.memedb.presentation.mapper

/**
 * Interface method to convert
 * domain model to view object
 * inorder to ensure loose coupling of domain data layer
 */
interface Mapper<out O, in I> {
    fun mapToView(input: I): O
}