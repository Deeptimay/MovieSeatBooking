package com.example.testassignmentgitrepo.domain.util

sealed interface NetworkResult<T : Any> {
    data class ApiSuccess<T : Any>(val data: T) : NetworkResult<T>
    data class ApiError<T : Any>(val code: Int, val message: String?) : NetworkResult<T>
    data class ApiException<T : Any>(val e: Throwable) : NetworkResult<T>
}

