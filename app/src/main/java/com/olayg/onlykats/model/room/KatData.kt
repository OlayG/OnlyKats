package com.olayg.onlykats.model.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "kat_table")
data class KatData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val age: Int
) : Parcelable