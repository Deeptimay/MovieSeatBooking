package com.example.testassignmentgitrepo.presentation.detailsScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.presentation.util.UiState
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RepoDetailsViewModel

    @Mock
    private lateinit var mockGetGithubRepoDetailsUseCase: GetGithubRepoDetailsUseCase

    private lateinit var testCoroutineDispatcher: TestCoroutineDispatcher
    private lateinit var testCoroutineScope: TestCoroutineScope

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testCoroutineDispatcher = TestCoroutineDispatcher()
        testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
        viewModel = RepoDetailsViewModel(mockGetGithubRepoDetailsUseCase)
    }

    @Test
    fun `getRepoDetails success`() = runTest {
        val responseMock = NetworkResult.ApiSuccess(MappedRepo(1, "Repo Name"))
        `when`(mockGetGithubRepoDetailsUseCase.execute(REPO_ID)).thenReturn(responseMock)

        viewModel.getRepoDetails(REPO_ID)

        viewModel.repoDetailsFlow.take(1).collect { uiState ->
            assertEquals(UiState.Success("Repo details"), uiState)
        }
    }

    @Test
    fun `getRepoDetails API error`() = runTest {
        val responseMock = NetworkResult.ApiError<MappedRepo>(404, "Not Found")
        `when`(mockGetGithubRepoDetailsUseCase.execute(REPO_ID)).thenReturn(responseMock)

        viewModel.getRepoDetails(REPO_ID)

        viewModel.repoDetailsFlow.take(1).collect { uiState ->
            assertEquals(UiState.Error(), uiState)
        }
    }

    @Test
    fun `getRepoDetails API exception`() = runTest {
        val responseMock = NetworkResult.ApiException<MappedRepo>(Exception("Network exception"))
        `when`(mockGetGithubRepoDetailsUseCase.execute(REPO_ID)).thenReturn(responseMock)

        viewModel.getRepoDetails(REPO_ID)

        viewModel.repoDetailsFlow.take(1).collect { uiState ->
            assertEquals(UiState.Error(), uiState)
        }
    }

    companion object {
        private const val REPO_ID = "repo_id"
    }
}