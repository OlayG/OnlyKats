package com.olayg.onlykats.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Image(
    val height: Int?,
    val id: String?,
    val url: String?,
    val width: Int?
) : Parcelable