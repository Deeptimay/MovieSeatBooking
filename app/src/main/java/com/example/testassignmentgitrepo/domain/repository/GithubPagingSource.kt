package com.example.testassignmentgitrepo.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.services.GithubApi
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val githubApi: GithubApi,
    private val query: String,
    private val repoMapper: RepoMapper
) : PagingSource<Int, MappedRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MappedRepo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getTrendingRepos(query, position, 10)
            val repos = repoMapper.fromEntityList(response.repo)

            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,//for paging forword and backword
                nextKey = if (repos.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MappedRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}