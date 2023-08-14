package com.example.testassignmentgitrepo.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.testassignmentgitrepo.data.model.Repo
import com.example.testassignmentgitrepo.data.services.GithubApi
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class GitHubRepoRepositoryImpl @Inject constructor(private val githubApi: GithubApi) :
    GitHubRepoRepository {
    override suspend fun fetchAllTrendingGitHubRepo(query: String): LiveData<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 1000,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query) }
        ).liveData
    }

    override suspend fun getGitHubRepoDetails(repoId: String): Flow<BaseResponse<Repo>> {
        return flow {
            emit(BaseResponse.Loading())
            val response = githubApi.getRepoDetails(repoId)
            if (response != null) {
                emit(BaseResponse.Success(true, "Success", response))
                Log.d("TAG", response.toString())
            } else {
                emit(BaseResponse.Error(false, "Error", null))
            }
        }
    }
}