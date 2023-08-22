package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCasesImpl.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class GetGithubRepoDetailsUseCaseTest {

    @Mock
    private var mockGitHubRepoRepository: GitHubRepoRepository = mock()

    private lateinit var useCase: GetGithubRepoDetailsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetGithubRepoDetailsUseCase(mockGitHubRepoRepository)
    }

    @Test
    fun `execute success`() = runBlocking {

        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(
            expectedResultSuccess
        )

        val actualResult = useCase(repoId)

        assertEquals(expectedResultSuccess, actualResult)
    }

    @Test
    fun `execute API error`() = runBlocking {

        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(expectedResultError)

        val actualResult = useCase(repoId)

        assertEquals(expectedResultError, actualResult)
    }

    @Test
    fun `execute API exception`() = runBlocking {
        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(
            expectedResultException
        )

        val actualResult = useCase(repoId)

        assertEquals(expectedResultException, actualResult)
    }

    companion object {
        const val repoId = "repoId"
        val expectedResultSuccess = NetworkResult.ApiSuccess(MappedRepo(1, "Repo Name"))
        val expectedResultError = NetworkResult.ApiError<MappedRepo>(404, "Not Found")
        val expectedResultException =
            NetworkResult.ApiException<MappedRepo>(Exception("Network exception"))
    }
}
