package com.olayg.onlykats.repo

import android.util.Log
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState
import kotlinx.coroutines.flow.flow

object KatRepo {
    private const val TAG = "KAT-REPO"

    const val NO_DATA_FOUND = "No data found."
    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(limit: String?, page: String?, order: String?) = flow {
        Log.d(TAG, "getKatState: emit(ApiState.Loading)")
        emit(ApiState.Loading)
        Log.d(TAG, "getKatState: katService.getKatImages(limit, page, order)")
        val queryMap = listOfNotNull(
            limit?.let { "limit" to it },
            page?.let { "page" to it },
            order?.let { "order" to it }
        ).toMap()
        val katResponse = katService.getKatImages(queryMap)
        Log.d(TAG, "getKatState: katResponse = ${katResponse.body()}")

        val state = if (katResponse.isSuccessful) {
            Log.d(TAG, "getKatState: katResponse.isSuccessful")
            if (katResponse.body().isNullOrEmpty()) {
                Log.d(TAG, "getKatState: Failure(\"No data found.\")")
                ApiState.Failure(NO_DATA_FOUND)
            } else {
                Log.d(TAG, "getKatState: Success(katResponse.body()!!)")
                ApiState.Success(katResponse.body()!!)
            }
        } else {
            Log.d(TAG, "getKatState: Failure(\"Error fetching data.\")")
            ApiState.Failure("Error fetching data.")
        }

        Log.d(TAG, "getKatState: emit(state)")
        emit(state)
    }

}