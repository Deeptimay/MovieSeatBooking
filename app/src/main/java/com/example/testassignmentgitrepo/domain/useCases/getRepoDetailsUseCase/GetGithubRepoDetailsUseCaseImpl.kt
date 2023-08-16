package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
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