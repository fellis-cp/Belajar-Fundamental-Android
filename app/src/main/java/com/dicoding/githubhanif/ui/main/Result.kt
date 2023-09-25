package com.dicoding.githubhanif.ui.main

sealed class Result {
    data class isSuccess<out T>(val data: T) : Result()
    data class isError(val exception: Throwable) : Result()
    data class isLoad(val isLoading: Boolean) : Result()
}
