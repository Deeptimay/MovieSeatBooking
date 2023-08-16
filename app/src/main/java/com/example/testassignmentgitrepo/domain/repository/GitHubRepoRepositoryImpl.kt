package com.example.testassignmentgitrepo.domain.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.services.GithubApi
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepoRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val repoMapper: RepoMapper
) :
    GitHubRepoRepository {

    override fun fetchAllTrendingGitHubRepo(query: String): Flow<PagingData<MappedRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 1000,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query, repoMapper) }
        ).flow
    }

    override suspend fun getGitHubRepoDetails(repoId: String): Flow<BaseResponse<MappedRepo>> {
        return flow {
            emit(BaseResponse.Loading())
            try {
                val response = repoMapper.mapFromDomainModel(githubApi.getRepoDetails(repoId))
                emit(BaseResponse.Success(true, "Success", response))
                Log.d("TAG", response.toString())
            } catch (exp: Exception) {
                emit(BaseResponse.Error(false, "Error", null))
            }
        }
    }
}