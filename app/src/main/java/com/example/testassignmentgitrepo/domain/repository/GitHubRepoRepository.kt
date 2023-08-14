package com.example.testassignmentgitrepo.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.model.Repo
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(SingletonComponent::class)
interface GitHubRepoRepository {
    suspend fun fetchAllTrendingGitHubRepo(query: String): LiveData<PagingData<Repo>>

    suspend fun getGitHubRepoDetails(repoId: String): Flow<BaseResponse<Repo>>
}