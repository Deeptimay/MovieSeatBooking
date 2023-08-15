package com.example.testassignmentgitrepo.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import kotlinx.coroutines.flow.Flow


interface GitHubRepoRepository {
    fun fetchAllTrendingGitHubRepo(query: String): LiveData<PagingData<Repo>>
    suspend fun getGitHubRepoDetails(repoId: String): Flow<BaseResponse<Repo>>
}