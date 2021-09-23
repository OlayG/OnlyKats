package com.olayg.onlykats.repo.local.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Category
import com.olayg.onlykats.model.Image
import com.olayg.onlykats.model.Weight
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.ByteArrayOutputStream
import java.util.*
@ProvidedTypeConverter
class KatConvertors {

    private val moshi by lazy { Moshi.Builder().build() }

//    private val breedListAdapter by lazy {
//        val type = Types.newParameterizedType(List::class.java, Breed::class.java)
//        return@lazy moshi.adapter<List<Breed>>(type)
//    }
//    private val categoryListAdapter by lazy {
//        val type = Types.newParameterizedType(List::class.java, Category::class.java)
//        return@lazy moshi.adapter<List<Category>?>(type)
//    }

    private val imageAdapter by lazy {
        val type = Types.newParameterizedType(Image::class.java)
        return@lazy moshi.adapter<Image>(type)
    }

    private inline fun <reified T> getGenericAdapterTwo(): JsonAdapter<T>{
        val type = Types.newParameterizedType(T::class.java)
        return moshi.adapter<T>(type)
    }

    private inline fun <reified T> getGenericAdapter(): JsonAdapter<List<T>>? {
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter<List<T>>(type)
    }

    @TypeConverter
    fun stringToBreedsList(data: String?): List<Breed> {
        return data?.let { getGenericAdapter<Breed>()?.fromJson(it) } ?: emptyList()
    }

    @TypeConverter
    fun breedsListToString(someObjects: List<Breed>): String? {
        return getGenericAdapter<Breed>()?.toJson(someObjects)
    }


    @TypeConverter
    fun stringToCategoryList(data: String?): List<Category> {
        return data?.let { getGenericAdapter<Category>()?.fromJson(it) } ?: emptyList()
    }

    @TypeConverter
    fun categoryListToString(someObjects: List<Category>?): String? {
        return getGenericAdapter<Category>()?.toJson(someObjects)
    }

    @TypeConverter
    fun imageToString(someObjects: Image?): String? {
        return imageAdapter?.toJson(someObjects)
    }

    @TypeConverter
    fun stringToImage(data: String?): Image? {
        return data?.let { getGenericAdapterTwo<Image>().fromJson(it) }
    }

    @TypeConverter
    fun weightToString(someObjects: Weight?): String? {
        return getGenericAdapterTwo<Weight>().toJson(someObjects)
    }
    @TypeConverter
    fun stringToWeight(data: String?): Weight?{
        return data?.let { getGenericAdapterTwo<Weight>().fromJson(it) }
    }

}

class convert {
    fun fromImage(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun toImage(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}