package com.example.testassignmentgitrepo.data.network

sealed class BaseResponse<T>(
    val status: Boolean? = null,
    val message: String? = null,
    val data: T? = null
) {
    class Success<T>(status: Boolean, message: String, data: T) :
        BaseResponse<T>(status, message, data)

    class Error<T>(status: Boolean, message: String, data: T?) :
        BaseResponse<T>(status, message, data)

    class Loading<T> : BaseResponse<T>()
}
