package com.example.testassignmentgitrepo.data.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetGithubRepoDetailsUseCaseImpl @Inject constructor(
    private val gitHubRepoRepository: GitHubRepoRepository
) :
    GetGithubRepoDetailsUseCase {
    override suspend operator fun invoke(repoId: String): NetworkResult<MappedRepo> {
        return gitHubRepoRepository.getGitHubRepoDetails(repoId)
    }
}