package com.example.testassignmentgitrepo.presentation.detailsScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCasesImpl.GetGithubRepoDetailsUseCase
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var mockUseCase: GetGithubRepoDetailsUseCase = mock()

    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = RepoDetailsViewModel(mockUseCase)
    }

    @Test
    fun `test getRepoDetails updates repoDetailsFlow with success state`() =
        runTest {

            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponseSuccess)

            viewModel.getRepoDetails(REPO_ID)
            delay(100)

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
            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponseError)

            viewModel.getRepoDetails(REPO_ID)
            delay(100)

            val result = viewModel.repoDetailsFlow.value
            assert(result is UiState.Error)
        }

    @Test
    fun `test getRepoDetails updates repoDetailsFlow with exception state`() =
        runTest {
            Mockito.`when`(mockUseCase(REPO_ID)).thenReturn(mockResponseException)

            viewModel.getRepoDetails(REPO_ID)
            delay(100)

            val result = viewModel.repoDetailsFlow.value
            assert(result is UiState.Error)
        }

    companion object {
        private const val REPO_ID = "repo_id"
        val mockData = UiState.Success(MappedRepo(1, "Repo Name"))
        val mockResponseSuccess = NetworkResult.ApiSuccess(MappedRepo(1, "Repo Name"))
        val mockResponseError = NetworkResult.ApiError<MappedRepo>(ErrorTypes.CustomError(404, "Not found"))
        val mockResponseException =
            NetworkResult.ApiError<MappedRepo>(ErrorTypes.ExceptionError(Exception("Test exception")))
    }
}