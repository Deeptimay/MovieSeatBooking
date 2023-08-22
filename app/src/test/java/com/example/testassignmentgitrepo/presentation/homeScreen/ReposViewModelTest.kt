package com.example.testassignmentgitrepo.presentation.homeScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCasesImpl.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.ErrorTypes
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class ReposViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var mockFetchGithubRepoUseCase: FetchGithubRepoUseCase = mock()

    private lateinit var viewModel: ReposViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ReposViewModel(mockFetchGithubRepoUseCase)
    }

    @Test
    fun `getRepoList success`() = runTest {
        `when`(mockFetchGithubRepoUseCase(DEFAULT_QUERY)).thenReturn(responseMockSuccess)

        viewModel.getRepoList()
        delay(100)

        val result = viewModel.repoFlow.value
        assertEquals(UiState.Success(listOf(MappedRepo(1, "Repo Name"))), result)
    }

    @Test
    fun `getRepoList API error`() = runTest {
        `when`(mockFetchGithubRepoUseCase(DEFAULT_QUERY)).thenReturn(responseMockError)

        viewModel.getRepoList()
        delay(100)

        // Then repoDetailsFlow should emit an Error state
        val result = viewModel.repoFlow.value
        assert(result is UiState.Error)
    }

    @Test
    fun `getRepoList API exception`() = runTest {

        `when`(mockFetchGithubRepoUseCase(DEFAULT_QUERY)).thenReturn(responseMockException)

        viewModel.getRepoList()
        delay(100)

        val result = viewModel.repoFlow.value
        assert(result is UiState.Error)
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
        val responseMockSuccess = NetworkResult.ApiSuccess(listOf(MappedRepo(1, "Repo Name")))
        val responseMockError = NetworkResult.ApiError<List<MappedRepo>>(ErrorTypes.CustomError(404, "Not Found"))
        val responseMockException =
            NetworkResult.ApiError<List<MappedRepo>>(ErrorTypes.ExceptionError(Exception("Network exception")))
    }
}