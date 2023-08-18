package com.example.testassignmentgitrepo.presentation.homeScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.presentation.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockFetchGithubRepoUseCase: FetchGithubRepoUseCase

    private lateinit var viewModel: ReposViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = ReposViewModel(mockFetchGithubRepoUseCase)
    }

    @After
    fun teatDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepoList success`() = runTest {
        val responseMock = NetworkResult.ApiSuccess(listOf(MappedRepo(1, "Repo Name")))
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()
        delay(100)

        val result = viewModel.repoFlow.value
        assertEquals(UiState.Success(listOf(MappedRepo(1, "Repo Name"))), result)
    }

    @Test
    fun `getRepoList API error`() = runTest {
        val responseMock = NetworkResult.ApiError<List<MappedRepo>>(404, "Not Found")
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()
        delay(100)

        // Then repoDetailsFlow should emit an Error state
        val result = viewModel.repoFlow.value
        assert(result is UiState.Error)
    }

    @Test
    fun `getRepoList API exception`() = runTest {
        val responseMock =
            NetworkResult.ApiException<List<MappedRepo>>(Exception("Network exception"))
        `when`(mockFetchGithubRepoUseCase.execute(DEFAULT_QUERY)).thenReturn(responseMock)

        viewModel.getRepoList()
        delay(100)

        val result = viewModel.repoFlow.value
        assert(result is UiState.Error)
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }
}