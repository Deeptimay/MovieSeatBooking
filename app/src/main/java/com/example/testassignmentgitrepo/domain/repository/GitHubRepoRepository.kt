package com.example.testassignmentgitrepo.domain.repository

import androidx.paging.PagingData
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.BaseResponse
import kotlinx.coroutines.flow.Flow


interface GitHubRepoRepository {
    fun fetchAllTrendingGitHubRepo(query: String): Flow<PagingData<MappedRepo>>
    suspend fun getGitHubRepoDetails(repoId: String): Flow<BaseResponse<MappedRepo>>
}