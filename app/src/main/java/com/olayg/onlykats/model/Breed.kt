package com.olayg.onlykats.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Breed(
    val adaptability: Int?,
    @Json(name = "affection_level")
    val affectionLevel: Int?,
    @Json(name = "alt_names")
    val altNames: String?,
    @Json(name = "cfa_url")
    val cfaUrl: String?,
    @Json(name = "child_friendly")
    val childFriendly: Int?,
    @Json(name = "country_code")
    val countryCode: String?,
    @Json(name = "country_codes")
    val countryCodes: String?,
    val description: String?,
    @Json(name = "dog_friendly")
    val dogFriendly: Int?,
    @Json(name = "energy_level")
    val energyLevel: Int?,
    val experimental: Int?,
    val grooming: Int?,
    val hairless: Int?,
    @Json(name = "health_issues")
    val healthIssues: Int?,
    val hypoallergenic: Int?,
    @PrimaryKey val id: String,
    val image: Image?,
    val indoor: Int?,
    val intelligence: Int?,
    val lap: Int?,
    @Json(name = "life_span")
    val lifeSpan: String?,
    val name: String?,
    val natural: Int?,
    val origin: String?,
    val rare: Int?,
    @Json(name = "reference_image_id")
    val referenceImageId: String?,
    val rex: Int?,
    @Json(name = "shedding_level")
    val sheddingLevel: Int?,
    @Json(name = "short_legs")
    val shortLegs: Int?,
    @Json(name = "social_needs")
    val socialNeeds: Int?,
    @Json(name = "stranger_friendly")
    val strangerFriendly: Int?,
    @Json(name = "suppressed_tail")
    val suppressedTail: Int?,
    val temperament: String?,
    val url: String?,
    @Json(name = "vcahospitals_url")
    val vcaHospitalsUrl: String?,
    @Json(name = "vetstreet_url")
    val vetStreetUrl: String?,
    val vocalisation: Int?,
    val weight: Weight?,
    @Json(name = "wikipedia_url")
    val wikipediaUrl: String?
) : Parcelable