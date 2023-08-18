package com.example.testassignmentgitrepo.presentation.homeScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.presentation.util.UiState
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class ReposViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ReposViewModel

    @Mock
    private lateinit var mockFetchGithubRepoUseCase: FetchGithubRepoUseCase

    private lateinit var testCoroutineDispatcher: TestCoroutineDispatcher
    private lateinit var testCoroutineScope: TestCoroutineScope

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testCoroutineDispatcher = TestCoroutineDispatcher()
        testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
        viewModel = ReposViewModel(mockFetchGithubRepoUseCase)
    }

    @Test
    fun `getRepoList success`() = runTest {
        val responseMock = NetworkResult.ApiSuccess(listOf(MappedRepo(1, "Repo Name")))
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()

        viewModel.repoFlow.collect { uiState ->
            assertEquals(UiState.Success(listOf("Repo1", "Repo2")), uiState)
        }
    }

    @Test
    fun `getRepoList API error`() = runTest {
        val responseMock = NetworkResult.ApiError<List<MappedRepo>>(404, "Not Found")
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()

        viewModel.repoFlow.collect { uiState ->
            assertEquals(UiState.Error(), uiState)
        }
    }

    @Test
    fun `getRepoList API exception`() = runTest {
        val responseMock =
            NetworkResult.ApiException<List<MappedRepo>>(Exception("Network exception"))
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()

        viewModel.repoFlow.collect { uiState ->
            assertEquals(UiState.Error(), uiState)
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }
}