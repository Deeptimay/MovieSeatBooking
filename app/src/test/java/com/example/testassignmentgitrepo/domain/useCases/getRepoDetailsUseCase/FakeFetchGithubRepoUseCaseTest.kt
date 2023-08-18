package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.data.repository.GithubPagingSource
import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeFetchGithubRepoUseCaseTest @Inject constructor(
    private val githubApi: GithubApi,
    private val repoMapper: RepoMapper
) : FetchGithubRepoUseCase {

    //    private val items2: = (0..100).map(Any::toString)
//
//    private val items: List<MappedRepo> = listOf(MappedRepo())
//
//    private val pagingSourceFactory = items.asPagingSourceFactory()
//
//    private val pagingSource = pagingSourceFactory()
//    private val pagingSource: Flow<PagingData<MappedRepo>> =
//        Flow<PagingData<MappedRepo>>()

    override fun execute(query: String): Flow<PagingData<MappedRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 1000,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query, repoMapper) }
        ).flow
    }
}