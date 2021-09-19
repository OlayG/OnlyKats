package com.olayg.onlykats.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Kat(
    val id: String,
    val height: Int,
    val url: String,
    val width: Int,
    val breeds: List<Breed> = listOf(),
    val categories: List<Category>?,
)