package com.olayg.onlykats.repo

import android.util.Log
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState
import kotlinx.coroutines.flow.flow

// TODO: 9/11/21 Update breeds method to fetch from katService and return correct API state
// TODO: 9/11/21 Update query map to all possible multiple queries
object KatRepo {
    private const val TAG = "KAT-REPO"
    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(queries: Queries) = flow {
        Log.d(TAG, "getKatState: emit(ApiState.Loading)")
        emit(ApiState.Loading)
        Log.d(TAG, "getKatState: katService.getData(limit, page, order)")

        val state = if (queries.endPoint != null) {
            val katResponse = katService.getKatImages(queries.asQueryMap)
            Log.d(TAG, "getKatState: katResponse = ${katResponse.body()}")

            if (katResponse.isSuccessful) {
                Log.d(TAG, "getKatState: katResponse.isSuccessful")
                if (katResponse.body().isNullOrEmpty()) {
                    Log.d(TAG, "getKatState: EndOfPage")
                    ApiState.EndOfPage
                } else {
                    Log.d(TAG, "getKatState: Success(katResponse.body()!!)")
                    ApiState.Success(katResponse.body()!!)
                }
            } else {
                Log.d(TAG, "getKatState: Failure(\"Error fetching data.\")")
                ApiState.Failure("Error fetching data.")
            }
        } else ApiState.Failure("Endpoint is null")

        Log.d(TAG, "getKatState: emit(state)")
        emit(state)
    }

    fun getBreedState(queries: Queries) = flow {
        emit(ApiState.Loading)
//        kotlinx.coroutines.delay(500)
//        emit(ApiState.Success(""))
        val state = if (queries.endPoint != null) {
            val breedResponse = katService.getBreeds(queries.asQueryMap)
            Log.d(TAG, "getBreedState: breedResponse = ${breedResponse.body()}")

            if (breedResponse.isSuccessful) {
                Log.d(TAG, "getBreedState: breedResponse.isSuccessful")
                if (breedResponse.body().isNullOrEmpty()) {
                    Log.d(TAG, "getBreedState: EndOfPage")
                    ApiState.EndOfPage
                } else {
                    Log.d(TAG, "getBreedState: Success(breedResponse.body()!!)")
                    ApiState.Success(breedResponse.body()!!)
                }
            } else {
                Log.d(TAG, "getBreedState: Failure(\"Error fetching data.\")")
                ApiState.Failure("Error fetching data.")
            }
        } else ApiState.Failure("Endpoint is null")

        Log.d(TAG, "getBreedState: emit(state)")
        emit(state)
    }

    private val Queries.asQueryMap: Map<String, Any>
        get() = listOfNotNull(
            "limit" to limit,
            page?.let { "page" to it }
        ).toMap()
}