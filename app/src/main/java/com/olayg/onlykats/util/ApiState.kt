package com.olayg.onlykats.util

sealed class ApiState<out R> {
    data class Success<T>(val data: T) : ApiState<T>()
    data class Failure(val errorMsg: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}
