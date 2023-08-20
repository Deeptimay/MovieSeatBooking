package com.example.testassignmentgitrepo.domain.repositoryAbstraction

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult


interface GitHubRepoRepository {
    suspend fun fetchAllTrendingGitHubRepo(query: String): NetworkResult<List<MappedRepo>>
    suspend fun getGitHubRepoDetails(repoId: String): NetworkResult<MappedRepo>
}