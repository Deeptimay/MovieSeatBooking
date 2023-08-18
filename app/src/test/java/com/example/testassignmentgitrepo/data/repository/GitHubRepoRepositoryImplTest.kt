package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.models.Repo
import com.example.testassignmentgitrepo.domain.models.TrendingRepoResponse
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class GitHubRepoRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockApi: GithubApi

    private lateinit var repository: GitHubRepoRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepoRepositoryImpl(mockApi, RepoMapper())
    }

    @Test
    fun `getGitHubRepoDetails success`() = runBlocking {
        val responseMock = Response.success(Repo(1, "Repo Name"))
        `when`(mockApi.getRepoDetails("repoId")).thenReturn(responseMock)

        val expectedResult = MappedRepo(1, "Repo Name")
        val actualResult = repository.getGitHubRepoDetails("repoId")

        assertEquals(NetworkResult.ApiSuccess(expectedResult), actualResult)
    }

    @Test
    fun `getGitHubRepoDetails API error`() = runBlocking {
        val responseMock = Response.error<Repo>(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(mockApi.getRepoDetails("repoId")).thenReturn(responseMock)

        val actualResult = repository.getGitHubRepoDetails("repoId")

        assertEquals(NetworkResult.ApiError<Repo>(404, "Not Found"), actualResult)
    }

    @Test
    fun `getGitHubRepoDetails HTTP exception`() = runBlocking {
        val httpException = retrofit2.HttpException(
            Response.error<Repo>(
                500,
                "Internal Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        `when`(mockApi.getRepoDetails("repoId")).thenThrow(httpException)

        val actualResult = repository.getGitHubRepoDetails("repoId")

        assertEquals(NetworkResult.ApiError<Repo>(500, "Internal Server Error"), actualResult)
    }

    @Test
    fun `getGitHubRepoDetails general exception`() = runBlocking {
        val exception = Exception("Some exception")
        `when`(mockApi.getRepoDetails("repoId")).thenThrow(exception)

        val actualResult = repository.getGitHubRepoDetails("repoId")

        assertEquals(NetworkResult.ApiException<Repo>(exception), actualResult)
    }

    @Test
    fun `fetchAllTrendingGitHubRepo success`() = runBlocking {
        val responseMock = Response.success(
            TrendingRepoResponse(
                1, true,
                arrayListOf(Repo(1, "Repo Name"))
            )
        )
        `when`(mockApi.getTrendingRepoList("query", 1, 100)).thenReturn(responseMock)

        val expectedResult = listOf(MappedRepo(1, "Repo Name"))
        val actualResult = repository.fetchAllTrendingGitHubRepo("query")

        assertEquals(NetworkResult.ApiSuccess(expectedResult), actualResult)
    }

    @Test
    fun `fetchAllTrendingGitHubRepo API error`() = runBlocking {
        val responseMock = Response.error<TrendingRepoResponse>(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(mockApi.getTrendingRepoList("query", 1, 100)).thenReturn(responseMock)

        val actualResult = repository.fetchAllTrendingGitHubRepo("query")

        assertEquals(NetworkResult.ApiError<Repo>(404, "Not Found"), actualResult)
    }

    @Test
    fun `fetchAllTrendingGitHubRepo HTTP exception`() = runBlocking {
        val httpException = HttpException(
            Response.error<TrendingRepoResponse>(
                500,
                "Internal Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        `when`(mockApi.getTrendingRepoList("query", 1, 100)).thenThrow(httpException)

        val actualResult = repository.fetchAllTrendingGitHubRepo("query")

        assertEquals(NetworkResult.ApiError<Repo>(500, "Internal Server Error"), actualResult)
    }

    @Test
    fun `fetchAllTrendingGitHubRepo general exception`() = runBlocking {
        val exception = Exception("Some exception")
        `when`(mockApi.getTrendingRepoList("query", 1, 100)).thenThrow(exception)

        val actualResult = repository.fetchAllTrendingGitHubRepo("query")

        assertEquals(NetworkResult.ApiException<Repo>(exception), actualResult)
    }
}