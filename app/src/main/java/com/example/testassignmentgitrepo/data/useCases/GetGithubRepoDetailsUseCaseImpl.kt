package com.example.testassignmentgitrepo.data.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.data.network.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGithubRepoDetailsUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    GetGithubRepoDetailsUseCase {
    override suspend fun execute(repoId: String): Flow<BaseResponse<MappedRepo>> {
        return gitHubRepoRepository.getGitHubRepoDetails(repoId)
    }
}