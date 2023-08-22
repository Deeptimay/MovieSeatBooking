package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.domain.util.ErrorTypes
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseRepository @Inject constructor() {

    suspend fun <T : Any> performApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let { NetworkResult.ApiSuccess(it) } ?: kotlin.run {
                    NetworkResult.ApiError(
                        ErrorTypes.CustomError(
                            code = response.code(),
                            internalMessage = response.errorBody().toString()
                        )
                    )
                }
            } else {
                NetworkResult.ApiError(
                    ErrorTypes.ServerError(
                        code = response.code(),
                        internalMessage = response.errorBody().toString()
                    )
                )
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(ErrorTypes.HttpExceptionError(e))
        } catch (e: IOException) {
            NetworkResult.ApiError(ErrorTypes.IOExceptionError(e))
        } catch (e: SocketTimeoutException) {
            NetworkResult.ApiError(ErrorTypes.TimeoutError(e))
        } catch (e: Throwable) {
            NetworkResult.ApiError(ErrorTypes.ExceptionError(e))
        }
    }
}
