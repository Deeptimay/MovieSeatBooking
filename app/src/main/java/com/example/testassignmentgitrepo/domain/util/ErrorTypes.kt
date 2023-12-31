package com.example.testassignmentgitrepo.domain.util

import java.net.SocketTimeoutException
import okio.IOException
import retrofit2.HttpException

sealed class ErrorTypes : Throwable() {
    data class ExceptionError(val exception: Throwable) : ErrorTypes()
    data class HttpExceptionError(val exception: HttpException) : ErrorTypes()
    data class IOExceptionError(val exception: IOException) : ErrorTypes()
    data class TimeoutError(val exception: SocketTimeoutException) : ErrorTypes()
    data class ServerError(val code: Int, val internalMessage: String) : ErrorTypes()
    data class CustomError(val code: Int, val internalMessage: String) : ErrorTypes()
}
