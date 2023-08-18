package com.example.testassignmentgitrepo.domain.repository

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult


interface GitHubRepoRepository {
    suspend fun fetchAllTrendingGitHubRepo(query: String): NetworkResult<List<MappedRepo>>
    suspend fun getGitHubRepoDetails(repoId: String): NetworkResult<MappedRepo>
}