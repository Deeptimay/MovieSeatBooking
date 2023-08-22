package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCasesImpl.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class FetchGithubRepoUseCaseTest {

    @Mock
    private var mockGitHubRepoRepository: GitHubRepoRepository = mock()

    private lateinit var useCase: FetchGithubRepoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = FetchGithubRepoUseCase(mockGitHubRepoRepository)
    }

    @Test
    fun `execute success`() = runBlocking {
        val query = "query"
        val expectedResult = NetworkResult.ApiSuccess(listOf(MappedRepo(1, "Repo Name")))
        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(expectedResult)

        val actualResult = useCase(query)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `execute API error`() = runBlocking {
        val query = "query"
        val expectedResult = NetworkResult.ApiError<List<MappedRepo>>(404, "Not Found")
        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(expectedResult)

        val actualResult = useCase(query)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `execute API exception`() = runBlocking {
        val query = "query"
        val expectedResult =
            NetworkResult.ApiException<List<MappedRepo>>(Exception("Network exception"))
        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(expectedResult)

        val actualResult = useCase(query)

        assertEquals(expectedResult, actualResult)
    }
}
