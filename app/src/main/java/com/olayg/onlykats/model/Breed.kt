package com.olayg.onlykats.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
    val id: String?,
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
    @Json(name = "vcahospitals_url")
    val vcaHospitalsUrl: String?,
    @Json(name = "vetstreet_url")
    val vetStreetUrl: String?,
    val vocalisation: Int?,
    val weight: Weight?,
    @Json(name = "wikipedia_url")
    val wikipediaUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        TODO("image"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("weight"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adaptability)
        parcel.writeValue(affectionLevel)
        parcel.writeString(altNames)
        parcel.writeString(cfaUrl)
        parcel.writeValue(childFriendly)
        parcel.writeString(countryCode)
        parcel.writeString(countryCodes)
        parcel.writeString(description)
        parcel.writeValue(dogFriendly)
        parcel.writeValue(energyLevel)
        parcel.writeValue(experimental)
        parcel.writeValue(grooming)
        parcel.writeValue(hairless)
        parcel.writeValue(healthIssues)
        parcel.writeValue(hypoallergenic)
        parcel.writeString(id)
        parcel.writeValue(indoor)
        parcel.writeValue(intelligence)
        parcel.writeValue(lap)
        parcel.writeString(lifeSpan)
        parcel.writeString(name)
        parcel.writeValue(natural)
        parcel.writeString(origin)
        parcel.writeValue(rare)
        parcel.writeString(referenceImageId)
        parcel.writeValue(rex)
        parcel.writeValue(sheddingLevel)
        parcel.writeValue(shortLegs)
        parcel.writeValue(socialNeeds)
        parcel.writeValue(strangerFriendly)
        parcel.writeValue(suppressedTail)
        parcel.writeString(temperament)
        parcel.writeString(vcaHospitalsUrl)
        parcel.writeString(vetStreetUrl)
        parcel.writeValue(vocalisation)
        parcel.writeString(wikipediaUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Breed> {
        override fun createFromParcel(parcel: Parcel): Breed {
            return Breed(parcel)
        }

        override fun newArray(size: Int): Array<Breed?> {
            return arrayOfNulls(size)
        }
    }
}