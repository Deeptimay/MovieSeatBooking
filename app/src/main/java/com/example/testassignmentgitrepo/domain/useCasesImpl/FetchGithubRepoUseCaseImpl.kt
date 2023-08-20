package com.example.testassignmentgitrepo.domain.useCasesImpl

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCaseAbstraction.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FetchGithubRepoUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    FetchGithubRepoUseCase {
    override suspend operator fun invoke(query: String): NetworkResult<List<MappedRepo>> {
        return gitHubRepoRepository.fetchAllTrendingGitHubRepo(query)
    }
}