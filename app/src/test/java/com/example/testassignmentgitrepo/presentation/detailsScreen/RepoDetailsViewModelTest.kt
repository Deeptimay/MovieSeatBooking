package com.example.testassignmentgitrepo.presentation.detailsScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCaseAbstraction.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.presentation.ui.UiState
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockUseCase: GetGithubRepoDetailsUseCase

    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = RepoDetailsViewModel(mockUseCase)
    }

    @Test
    fun `test getRepoDetails updates repoDetailsFlow with success state`() =
        runTest {
            // Given a repoId and a successful response
            val mockData = UiState.Success(MappedRepo(1, "Repo Name"))
            val mockResponse = NetworkResult.ApiSuccess(MappedRepo(1, "Repo Name"))
            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponse)

            viewModel.getRepoDetails(REPO_ID)
            delay(100)

            // Then repoDetailsFlow should emit an Success state
            val result = viewModel.repoDetailsFlow.value
            assertEquals(
                UiState.Success(MappedRepo(1, "Repo Name")),
                result
            )

            assertEquals(mockData, (result as UiState.Success<MappedRepo>))
        }

    @Test
    fun `test getRepoDetails updates repoDetailsFlow with error state`() =
        runTest {
            // Given a repoId and an error response
            val mockResponse = NetworkResult.ApiError<MappedRepo>(404, "Not found")
            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponse)

            // When calling getRepoDetails
            viewModel.getRepoDetails(REPO_ID)
            delay(100)

            // Then repoDetailsFlow should emit an Error state
            val result = viewModel.repoDetailsFlow.value
            assert(result is UiState.Error)
        }

    @Test
    fun `test getRepoDetails updates repoDetailsFlow with exception state`() =
        runTest {
            // Given a repoId and an exception response
            val mockResponse =
                NetworkResult.ApiException<MappedRepo>(RuntimeException("Test exception"))
            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponse)

            // When calling getRepoDetails
            viewModel.getRepoDetails(REPO_ID)
            delay(100)

            // Then repoDetailsFlow should emit an Error state
            val result = viewModel.repoDetailsFlow.value
            assert(result is UiState.Error)
        }

    companion object {
        private const val REPO_ID = "repo_id"
    }
}