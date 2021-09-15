package com.olayg.onlykats.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val height: Int?,
    val id: String?,
    val url: String?,
    val width: Int?
)