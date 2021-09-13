package com.olayg.onlykats.repo

import android.util.Log
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState
import kotlinx.coroutines.flow.flow
// TODO: 9/11/21 Update query map to all possible multiple queries

object KatRepo {
    private const val TAG = "KAT-REPO"
    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(queries: Queries) = flow {
        emit(ApiState.Loading)

        val state = if (queries.endPoint != null) {
            val katResponse = katService.getKatImages(queries.asQueryMap)

            if (katResponse.isSuccessful) {
                if (katResponse.body().isNullOrEmpty()) {
                    ApiState.EndOfPage
                } else {
                    ApiState.Success(katResponse.body()!!)
                }
            } else {
                ApiState.Failure("Error fetching data.")
            }
        } else ApiState.Failure("Endpoint is null")

        emit(state)
    }

    fun getBreedState(queries: Queries) = flow {
        emit(ApiState.Loading)

        val state = if (queries.endPoint != null) {
            val breedResponse = katService.getBreeds(queries.asQueryMap)

            if (breedResponse.isSuccessful) {
                if (breedResponse.body().isNullOrEmpty()) {
                    ApiState.EndOfPage
                } else {
                    ApiState.Success(breedResponse.body()!!)
                }
            } else {
                ApiState.Failure("Error fetching data.")
            }
        } else ApiState.Failure("Endpoint is null")

        emit(state)
    }

    private val Queries.asQueryMap: Map<String, Any>
        get() = listOfNotNull(
            "limit" to limit,
            page?.let { "page" to it }
        ).toMap()
}