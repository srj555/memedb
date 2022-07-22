package com.srdroid.memedb.common

/**
 * Mapper Base class
 * takes input object and converts to
 * output object
 */
interface Mapper<out O, in I> {
    fun mapToOut(input: I): O
}