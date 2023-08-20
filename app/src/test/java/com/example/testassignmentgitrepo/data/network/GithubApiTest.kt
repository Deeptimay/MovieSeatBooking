package com.example.testassignmentgitrepo.data.network

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.data.models.TrendingRepoResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GithubApiTest {

    @Mock
    private lateinit var mockGithubApi: GithubApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getTrendingRepoList success`() = runBlocking {
        val fakeResponse = TrendingRepoResponse(
            totalCount = 100,
            incompleteResults = false,
            repo = arrayListOf(Repo(id = 1, name = "Repo 1"))
        )

        Mockito.`when`(
            mockGithubApi.getTrendingRepoList(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt()
            )
        )
            .thenReturn(Response.success(fakeResponse))

        val response = mockGithubApi.getTrendingRepoList("android", 1, 10)

        assertEquals(fakeResponse, response.body())
    }

    @Test
    fun `test getTrendingRepoList error`() = runBlocking {
        val errorResponseBody = ResponseBody.create(null, "Error message")
        val fakeErrorResponse = Response.error<TrendingRepoResponse>(400, errorResponseBody)

        Mockito.`when`(
            mockGithubApi.getTrendingRepoList(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt()
            )
        )
            .thenReturn(fakeErrorResponse)

        val response = mockGithubApi.getTrendingRepoList("android", 1, 10)

        assertEquals(400, response.code())
    }

    @Test
    fun `test getRepoDetails success`() = runBlocking {
        val fakeResponse = Repo(id = 1, name = "Repo 1")

        Mockito.`when`(mockGithubApi.getRepoDetails(Mockito.anyString()))
            .thenReturn(Response.success(fakeResponse))

        val response = mockGithubApi.getRepoDetails("repository_id")

        assertEquals(fakeResponse, response.body())
    }

    @Test
    fun `test getRepoDetails error`() = runBlocking {
        val errorResponseBody = ResponseBody.create(null, "Error message")
        val fakeErrorResponse = Response.error<Repo>(404, errorResponseBody)

        Mockito.`when`(mockGithubApi.getRepoDetails(Mockito.anyString()))
            .thenReturn(fakeErrorResponse)

        val response = mockGithubApi.getRepoDetails("repository_id")

        assertEquals(404, response.code())
    }
}
