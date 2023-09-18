package com.example.testassignmentgitrepo.data.network

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.data.models.TrendingRepoResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import retrofit2.Response

class GithubApiTest {

    @Mock
    private var mockGithubApi: GithubApi = mock()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getTrendingRepoList success`() = runBlocking {

        Mockito.`when`(
            mockGithubApi.getTrendingRepoList(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt()
            )
        )
            .thenReturn(Response.success(fakeResponseTrendingRepoResponse))

        val response = mockGithubApi.getTrendingRepoList("android", 1, 10)

        assertEquals(fakeResponseTrendingRepoResponse, response.body())
    }

    @Test
    fun `test getTrendingRepoList error`() = runBlocking {

        Mockito.`when`(
            mockGithubApi.getTrendingRepoList(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt()
            )
        )
            .thenReturn(fakeErrorResponseTrendingRepoResponse)

        val response = mockGithubApi.getTrendingRepoList("android", 1, 10)

        assertEquals(400, response.code())
    }

    @Test
    fun `test getRepoDetails success`() = runBlocking {

        Mockito.`when`(mockGithubApi.getRepoDetails(Mockito.anyString()))
            .thenReturn(Response.success(fakeResponse))

        val response = mockGithubApi.getRepoDetails("repository_id")

        assertEquals(fakeResponse, response.body())
    }

    @Test
    fun `test getRepoDetails error`() = runBlocking {

        Mockito.`when`(mockGithubApi.getRepoDetails(Mockito.anyString()))
            .thenReturn(fakeErrorResponse)

        val response = mockGithubApi.getRepoDetails("repository_id")

        assertEquals(404, response.code())
    }

    companion object {

        val fakeResponse = Repo(id = 1, name = "Repo 1")

        val fakeResponseTrendingRepoResponse = TrendingRepoResponse(
            totalCount = 100,
            incompleteResults = false,
            repo = arrayListOf(Repo(id = 1, name = "Repo 1"))
        )

        private val errorResponseBody = "Error message".toResponseBody(null)
        val fakeErrorResponse: Response<Repo> = Response.error<Repo>(404, errorResponseBody)
        val fakeErrorResponseTrendingRepoResponse: Response<TrendingRepoResponse> =
            Response.error<TrendingRepoResponse>(400, errorResponseBody)

    }
}
