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
        val repoId = "repoId"
        val expectedResult = NetworkResult.ApiSuccess(MappedRepo(1, "Repo Name"))
        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(expectedResult)

        val actualResult = useCase(repoId)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `execute API error`() = runBlocking {
        val repoId = "repoId"
        val expectedResult = NetworkResult.ApiError<MappedRepo>(404, "Not Found")
        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(expectedResult)

        val actualResult = useCase(repoId)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `execute API exception`() = runBlocking {
        val repoId = "repoId"
        val expectedResult = NetworkResult.ApiException<MappedRepo>(Exception("Network exception"))
        `when`(mockGitHubRepoRepository.getGitHubRepoDetails(repoId)).thenReturn(expectedResult)

        val actualResult = useCase(repoId)

        assertEquals(expectedResult, actualResult)
    }
}
