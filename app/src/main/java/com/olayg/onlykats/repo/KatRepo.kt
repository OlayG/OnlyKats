package com.olayg.onlykats.repo

import android.util.Log
import com.olayg.onlykats.repo.remote.RetrofitInstance
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.util.Order
import kotlinx.coroutines.flow.flow

object KatRepo {
    private const val TAG = "KAT-REPO"

    private val katService by lazy { RetrofitInstance.katService }

    fun getKatState(
        limit: Int,
        page: Int = 1,
        order: Order = Order.DESC
    ) = flow {
        Log.d(TAG, "getKatState: emit(ApiState.Loading)")
        emit(ApiState.Loading)

        Log.d(TAG, "getKatState: katService.getKatImages(limit, page, order)")
        val katResponse = katService.getKatImages(limit, page, order)
        Log.d(TAG, "getKatState: katResponse = ${katResponse.body()}")

        val state = if (katResponse.isSuccessful) {
            Log.d(TAG, "getKatState: katResponse.isSuccessful")
            if (katResponse.body().isNullOrEmpty()) {
                Log.d(TAG, "getKatState: Failure(\"No data found.\")")
                ApiState.Failure("No data found.")
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