package com.example.testassignmentgitrepo.presentation

import app.cash.turbine.test
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeFetchGithubRepoUseCaseTest
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeGetGithubRepoDetailsUseCaseTest
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.FakeGitHubApi
import com.example.testassignmentgitrepo.data.network.BaseResponse
import com.example.testassignmentgitrepo.data.network.BaseResponse.*
import com.example.testassignmentgitrepo.presentation.homeScreen.ReposViewModel
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
class ReposViewModelTest() {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ReposViewModel
    private lateinit var fakeFetchGithubRepoUseCaseTest: FakeFetchGithubRepoUseCaseTest
    private lateinit var fakeGetGithubRepoDetailsUseCaseTest: FakeGetGithubRepoDetailsUseCaseTest
    private lateinit var gitHubUseCaseWrapper: GitHubUseCaseWrapper
    private var fakeGitHubApi: FakeGitHubApi = FakeGitHubApi()

    @Before
    fun setUp() {
//        Dispatchers.setMain(mainDispatcherRule)
        fakeFetchGithubRepoUseCaseTest = FakeFetchGithubRepoUseCaseTest(fakeGitHubApi, RepoMapper())
        fakeGetGithubRepoDetailsUseCaseTest = FakeGetGithubRepoDetailsUseCaseTest()
        gitHubUseCaseWrapper = GitHubUseCaseWrapper(
            fakeFetchGithubRepoUseCaseTest,
            fakeGetGithubRepoDetailsUseCaseTest
        )
        viewModel = ReposViewModel(gitHubUseCaseWrapper)
    }

    @After
    fun tearDown() {
//        Dispatchers.resetMain()
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when initialized, repository emits loading and data`() = runTest {
//
//        val users = listOf(
//            User(
//                name = "User 1", age = 20
//            ), User(
//                name = "User 2", age = 30
//            )
//        )
//
//        assertEquals(UiState.Loading, viewModel.repoDetailsFlow.value)
//
//        repository.sendUsers(users)
//
//        viewModel.onRefresh()
//
//        assertSame(UiState.Success(users), viewModel.repoDetailsFlow.value)
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when initialized, repository emits loading and data (using turbine)`() = runTest {
//
//        val users = listOf(
//            User(
//                name = "User 1", age = 20
//            ), User(
//                name = "User 2", age = 30
//            )
//        )
//
//        viewModel.repoDetailsFlow.test {
//            val firstItem = awaitItem()
//            assertEquals(UiState.Loading, firstItem)
//
//            val secondItem = awaitItem()
//            assertSame(UiState.Success(users), secondItem)
//        }
//    }

//    var testDispatcher = StandardTestDispatcher()
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: ReposViewModel
//    private lateinit var fakeFetchGithubRepoUseCaseTest: FakeFetchGithubRepoUseCaseTest
//    private lateinit var fakeGetGithubRepoDetailsUseCaseTest: FakeGetGithubRepoDetailsUseCaseTest
//    private lateinit var gitHubUseCaseWrapper: GitHubUseCaseWrapper
//

    @Test
    fun `for success resource, repo details data must be available`() = runTest {
        fakeGetGithubRepoDetailsUseCaseTest.emit(BaseResponse.Success(true, "success", MappedRepo()))
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