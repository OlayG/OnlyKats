package com.olayg.onlykats.model

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@Entity
@JsonClass(generateAdapter = true)
@Parcelize
data class Weight(
    val imperial: String?,
    val metric: String?
) : Parcelable