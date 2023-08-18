package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import com.example.testassignmentgitrepo.domain.models.Repo
import com.example.testassignmentgitrepo.domain.models.TrendingRepoResponse
import com.example.testassignmentgitrepo.data.network.GithubApi

class FakeGitHubApi : GithubApi {
    override suspend fun getTrendingRepos(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): TrendingRepoResponse {
        return TrendingRepoResponse()
    }

    override suspend fun getRepoDetails(repoId: String): Repo {
        return Repo()
    }
}