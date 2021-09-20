package com.olayg.onlykats.repo

import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object KatRepo {
    private const val TAG = "KAT-REPO"
    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(queries: Queries) = flow {
        emit(ApiState.Loading)
        val state = if (queries.endPoint != null) {
            katService.getKatImages(queries.asQueryMap).getApiState()
        } else ApiState.Failure("Endpoint is null")
        emit(state)
    }

    fun getBreedState(queries: Queries) = flow {
        emit(ApiState.Loading)
        kotlinx.coroutines.delay(500)
        val state = if (queries.endPoint != null) {
            katService.getBreeds(queries.asQueryMap).getApiState()
        } else ApiState.Failure("Endpoint is null")
        emit(state)
    }

    private val Queries.asQueryMap: Map<String, Any>
        get() = listOfNotNull(
            "limit" to limit,
            page?.let { "page" to it }
        ).toMap()
}

private fun <T> Response<List<T>>.getApiState(): ApiState<List<T>> {
    return if (isSuccessful)
    {
        if (body().isNullOrEmpty()) { ApiState.EndOfPage }
        else { ApiState.Success(body()!!) }
    } else { ApiState.Failure("Error fetching data.") }
}