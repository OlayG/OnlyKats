package com.olayg.onlykats.repo.remote

import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

// TODO: 9/11/21 update getBreeds
interface KatService {

    @Headers("x-api-key: 1fa2c5c0-305d-4305-8edc-2efcbf37b9b4")
    @GET("v1/images/search")
    suspend fun getKatImages(
        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ): Response<List<Kat>>

    @GET("v1/breeds")
    suspend fun getBreeds(

        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ): Response<List<Breed>>

}