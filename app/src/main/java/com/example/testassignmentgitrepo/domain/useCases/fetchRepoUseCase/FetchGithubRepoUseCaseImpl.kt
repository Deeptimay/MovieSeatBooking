package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.model.Repo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchGithubRepoUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    FetchGithubRepoUseCase {
    override suspend fun getSearchResults(query: String): LiveData<PagingData<Repo>> {
        return gitHubRepoRepository.fetchAllTrendingGitHubRepo(query)
    }
}