package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FetchGithubRepoUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    FetchGithubRepoUseCase {
    override fun execute(query: String): LiveData<PagingData<Repo>> {
        return gitHubRepoRepository.fetchAllTrendingGitHubRepo(query)
    }
}