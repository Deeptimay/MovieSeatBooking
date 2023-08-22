package com.example.testassignmentgitrepo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.data.models.TrendingRepoResponse
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class GitHubRepoRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var mockBaseRepository: BaseRepository = mockk(relaxed = true)
    private var mockGithubApi: GithubApi = mockk()
    private var mockApiCallForRepoList: suspend () -> Response<TrendingRepoResponse> = mockk()
    private var mockApiCallForRepoDetails: suspend () -> Response<Repo> = mockk()
    private var mockRepoMapper: RepoMapper = mockk()

    private lateinit var repository: GitHubRepoRepositoryImpl

    @Before
    fun setUp() {
        repository = GitHubRepoRepositoryImpl(mockBaseRepository, mockGithubApi, mockRepoMapper)
    }


    @Test
    fun `fetchAllTrendingGitHubRepo success`() = runBlocking {

        coEvery { mockGithubApi.getTrendingRepoList(query, 1, 100) } returns successResponse
        coEvery { mockRepoMapper.fromEntityList(apiResponse.repo) } returns mappedRepoList
        coEvery { mockBaseRepository.performApiCall(mockApiCallForRepoList) } coAnswers {
            val apiCall: suspend () -> TrendingRepoResponse = arg(0)
            NetworkResult.ApiSuccess(apiCall.invoke())
        }

        val result = repository.fetchAllTrendingGitHubRepo(query)
        assertEquals(
            NetworkResult.ApiSuccess(mappedRepoList),
            inputData as NetworkResult.ApiSuccess
        )
    }

    @Test
    fun `getGitHubRepoDetails success`() = runBlocking {

        coEvery { mockGithubApi.getRepoDetails(repoId) } returns repoSuccessResponse
        coEvery { mockRepoMapper.mapRepoToMappedRepoModel(repo) } returns mappedRepo
        coEvery { mockBaseRepository.performApiCall(mockApiCallForRepoDetails) } coAnswers {
            val apiCall: suspend () -> Repo = arg(0)
            NetworkResult.ApiSuccess(apiCall.invoke())
        }

        val result = repository.getGitHubRepoDetails(repoId)
        assertEquals(
            NetworkResult.ApiSuccess(mappedRepo),
            inputDetailsData as NetworkResult.ApiSuccess
        )
    }

    companion object {

        val query = "android"
        const val repoId = "repoId"

        val mappedRepo = MappedRepo(1, "Repo Name")
        val repo = Repo(1, "Repo Name")

        private val responseBody = TrendingRepoResponse(
            1, true,
            arrayListOf(Repo(1, name = "Repo Name"))
        )

        val apiResponse = TrendingRepoResponse(1, true, arrayListOf(Repo(1, "Repo Name")))
        val successResponse: Response<TrendingRepoResponse> = Response.success(responseBody)

        val mappedRepoList = listOf(MappedRepo(1, "Repo Name"))
        val inputData = NetworkResult.ApiSuccess(arrayListOf(MappedRepo(1, name = "Repo Name")))
        val inputDetailsData = NetworkResult.ApiSuccess(MappedRepo(1, name = "Repo Name"))

        val repoSuccessResponse: Response<Repo> = Response.success(repo)
    }
}