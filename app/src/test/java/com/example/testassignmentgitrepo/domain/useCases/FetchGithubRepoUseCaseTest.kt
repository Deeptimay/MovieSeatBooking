package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCasesImpl.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.ErrorTypes
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

        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(
            expectedResultSuccess
        )

        val actualResult = useCase(query)

        assertEquals(expectedResultSuccess, actualResult)
    }

    @Test
    fun `execute API error`() = runBlocking {

        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(
            expectedResultError
        )
        val actualResult = useCase(query)

        assertEquals(expectedResultError, actualResult)
    }

    @Test
    fun `execute API exception`() = runBlocking {

        `when`(mockGitHubRepoRepository.fetchAllTrendingGitHubRepo(query)).thenReturn(
            expectedResultException
        )
        val actualResult = useCase(query)

        assertEquals(expectedResultException, actualResult)
    }

    companion object {
        const val query = "query"
        val expectedResultSuccess = NetworkResult.ApiSuccess(listOf(MappedRepo(1, "Repo Name")))
        val expectedResultError =
            NetworkResult.ApiError<List<MappedRepo>>(ErrorTypes.CustomError(404, "Not Found"))
        val expectedResultException =
            NetworkResult.ApiError<List<MappedRepo>>(ErrorTypes.ExceptionError(Exception("Network exception")))
    }
}
