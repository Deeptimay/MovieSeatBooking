package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGithubRepoDetailsUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    GetGithubRepoDetailsUseCase {
    override suspend fun getRepoDetails(repoId: String): Flow<BaseResponse<Repo>> {
        return gitHubRepoRepository.getGitHubRepoDetails(repoId)
    }
}