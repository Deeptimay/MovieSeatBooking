package com.example.testassignmentgitrepo.domain.useCasesImpl

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCaseAbstraction.GetGithubRepoDetailsUseCase
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