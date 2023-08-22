package com.example.testassignmentgitrepo.domain.util

sealed interface NetworkResult<T : Any> {
    data class ApiSuccess<T : Any>(var data: T) : NetworkResult<T>
    data class ApiError<T : Any>(val errorData: ErrorTypes) : NetworkResult<T>
}

