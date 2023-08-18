//package com.example.testassignmentgitrepo.domain.repository
//
//import androidx.paging.PagingSource
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.*
//
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import java.util.logging.Level.CONFIG
//
//class GithubPagingSourceTest {
//
//    private val mockPosts = listOf(
//        postFactory.createRedditPost(DEFAULT_SUBREDDIT),
//        postFactory.createRedditPost(DEFAULT_SUBREDDIT),
//        postFactory.createRedditPost(DEFAULT_SUBREDDIT)
//    )
//    private val fakeApi = FakeRedditApi().apply {
//        mockPosts.forEach { post -> addPost(post) }
//    }
//
//    @Test
//    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
//        val pagingSource = RedditPagingSource(
//            fakeApi,
//            DEFAULT_SUBREDDIT
//        )
//
//        val pager = TestPager(CONFIG, pagingSource)
//
//        val result = pager.refresh() as PagingSource.LoadResult.Page
//
//        // Write assertions against the loaded data
//        assertThat(result.data)
//            .containsExactlyElementsIn(mockPosts)
//            .inOrder()
//    }
//    @Before
//    fun setUp() {
//    }
//
//    @After
//    fun tearDown() {
//    }
//
//    @Test
//    fun load() {
//    }
//
//    @Test
//    fun getRefreshKey() {
//    }
//}