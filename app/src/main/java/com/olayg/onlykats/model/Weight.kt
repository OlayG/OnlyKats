package com.olayg.onlykats.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Weight(
    val imperial: String?,
    val metric: String?
) :Parcelable