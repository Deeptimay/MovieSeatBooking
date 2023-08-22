package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.data.models.TrendingRepoResponse
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class BaseRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var baseRepository: BaseRepository

    @Before
    fun setUp() {
        baseRepository = BaseRepository()
    }

    @Test
    fun `performApiCall success`() = runBlocking {

        coEvery { response.isSuccessful } returns true
        coEvery { response.body() } returns responseBody

        val apiCall: suspend () -> Response<TrendingRepoResponse> = { response }
        val result = baseRepository.performApiCall(apiCall)

        assertTrue(result is NetworkResult.ApiSuccess)
        assertEquals(NetworkResult.ApiSuccess(responseBody), result)
    }

    @Test
    fun `performApiCall API error`() = runBlocking {
        val apiCall: suspend () -> Response<TrendingRepoResponse> = { errorResponse }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(
            NetworkResult.ApiError<Repo>(errorCode, errorMessage).code,
            (result as NetworkResult.ApiError<Repo>).code
        )
    }

    @Test
    fun `performApiCall HTTP exception`() = runBlocking {
        val httpException = HttpException(errorResponse)

        val apiCall: suspend () -> Response<TrendingRepoResponse> = { throw httpException }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(
            NetworkResult.ApiError<Repo>(httpException.code(), httpException.message()),
            result
        )
    }

    @Test
    fun `performApiCall general exception`() = runBlocking {

        val exception = RuntimeException(exceptionMessage)

        val apiCall: suspend () -> Response<TrendingRepoResponse> = { throw exception }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(NetworkResult.ApiException<Repo>(exception), result)
    }

    companion object {
        const val exceptionMessage = "Test Exception"

        const val errorCode = 404
        const val errorMessage = "Not Found"

        val response = mockk<Response<TrendingRepoResponse>>()

        val responseBody = TrendingRepoResponse(
            1, true,
            arrayListOf(Repo(1, name = "Repo Name"))
        )

        val errorResponse: Response<TrendingRepoResponse> = Response.error<TrendingRepoResponse>(
            errorCode,
            ResponseBody.create("text/plain".toMediaTypeOrNull(), errorMessage)
        )
    }
}
