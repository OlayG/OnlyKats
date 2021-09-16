package com.olayg.onlykats.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Kat(
    val breeds: List<Breed> = listOf(),
    val categories: List<Category>?,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)