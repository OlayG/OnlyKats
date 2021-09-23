package com.olayg.onlykats.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@Entity
@JsonClass(generateAdapter = true)
@Parcelize
data class Image(
    @PrimaryKey val id: String?,
    val height: Int?,
    val url: String?,
    val width: Int?
): Parcelable
