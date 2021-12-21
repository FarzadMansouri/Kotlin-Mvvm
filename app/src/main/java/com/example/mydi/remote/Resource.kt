package com.example.mydi.remote

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorMessage: ResponseBody? = null,
        val errorCode: Int? = 0,
    ) : Resource<Nothing>()
}