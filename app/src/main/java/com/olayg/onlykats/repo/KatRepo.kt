package com.olayg.onlykats.repo

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import android.util.Log

import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState

object KatRepo {
    private const val TAG = "KAT-REPO"
    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(queries: Queries) = flow {
        Log.d(TAG, "getKatState: emit(ApiState.Loading)")
        emit(ApiState.Loading)
        Log.d(TAG, "getKatState: katService.getData(limit, page, order)")
        val state = if (queries.endPoint != null) {
            katService.getKatImages(queries.asQueryMap).getApiState()
        } else ApiState.Failure("Endpoint is null.")
        Log.d(TAG, "emit(state")
        emit(state)
    }

    fun getBreedState(queries: Queries) = flow {
        Log.d(TAG, "getBreedState: emit(ApiState.Loading)")
        emit(ApiState.Loading)
        Log.d(TAG, "getBreedState: katService.getData(limit, page, order)")
        val state = if (queries.endPoint != null) {
            katService.getBreeds(queries.asQueryMap).getApiState()
        } else ApiState.Failure("Endpoint is null")
        emit(state)
    }

    private fun <T> Response<List<T>>.getApiState(): ApiState<List<T>> {
        return if (isSuccessful) {
            if (body().isNullOrEmpty()) ApiState.EndOfPage
            else ApiState.Success(body()!!)
        } else ApiState.Failure("Error fetching data.")
    }

    private val Queries.asQueryMap: Map<String, Any>
        get() = listOfNotNull(
            "limit" to limit,
            page?.let { "page" to it }
        ).toMap()
}