package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.domain.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
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
                        code = response.code(),
                        message = response.errorBody().toString()
                    )
                }
            } else {
                NetworkResult.ApiError(
                    code = response.code(),
                    message = response.errorBody().toString()
                )
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResult.ApiException(e)
        }
    }
}
