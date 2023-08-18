package com.example.testassignmentgitrepo.presentation

import app.cash.turbine.test
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.network.BaseResponse
import com.example.testassignmentgitrepo.data.network.BaseResponse.*
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeFetchGithubRepoUseCaseTest
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeGetGithubRepoDetailsUseCaseTest
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeGitHubApi
import com.example.testassignmentgitrepo.presentation.detailsScreen.RepoDetailsViewModel
import com.example.testassignmentgitrepo.util.MainDispatcherRule
import com.example.testassignmentgitrepo.util.UiState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RepoDetailsViewModelTest() {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RepoDetailsViewModel
    private lateinit var fakeFetchGithubRepoUseCaseTest: FakeFetchGithubRepoUseCaseTest
    private lateinit var fakeGetGithubRepoDetailsUseCaseTest: FakeGetGithubRepoDetailsUseCaseTest
    private var fakeGitHubApi: FakeGitHubApi = FakeGitHubApi()

    @Before
    fun setUp() {
//        Dispatchers.setMain(mainDispatcherRule)
        fakeFetchGithubRepoUseCaseTest = FakeFetchGithubRepoUseCaseTest(fakeGitHubApi, RepoMapper())
        fakeGetGithubRepoDetailsUseCaseTest = FakeGetGithubRepoDetailsUseCaseTest()
        viewModel = RepoDetailsViewModel(fakeGetGithubRepoDetailsUseCaseTest)
    }

    @After
    fun tearDown() {
//        Dispatchers.resetMain()
    }

    @Test
    fun `for success resource, repo details data must be available`() = runTest {
        fakeGetGithubRepoDetailsUseCaseTest.emit(
            BaseResponse.Success(
                true,
                "success",
                MappedRepo()
            )
        )
        viewModel.repoDetailsFlow.test {
            val dataItem = awaitItem()
            assertSame(UiState.Success(MappedRepo()), dataItem)
        }
    }

    @Test
    fun `for loading resource, repo details data should be null && isLoading should be true`() =
        runTest {
            fakeGetGithubRepoDetailsUseCaseTest.emit(BaseResponse.Loading())

            viewModel.repoDetailsFlow.test {
                val loadingItem = awaitItem()
                assertEquals(UiState.Loading, loadingItem)
            }
        }

    @Test
    fun `for error resource, repo details data should be null && hasError should be true && errorMessage should be Error`() =
        runTest {
            fakeGetGithubRepoDetailsUseCaseTest.emit(BaseResponse.Error(false, "Error", null))
            viewModel.repoDetailsFlow.test {
                val errorItem = awaitItem()
                assertSame(UiState.Error(), errorItem)
            }
        }

//    @Test
//    fun test_separators_are_added_every_10_items() = runTest {
//
//        // Get the Flow of PagingData from the ViewModel with the separator transformations applied
//        val items: Flow<PagingData<String>> = fakeFetchGithubRepoUseCaseTest.items
//
//        val snapshot: List<String> = items.asSnapshot()
//
//        // With the asSnapshot complete, you can now verify that the snapshot
//        // has the expected separators.
//    }
}