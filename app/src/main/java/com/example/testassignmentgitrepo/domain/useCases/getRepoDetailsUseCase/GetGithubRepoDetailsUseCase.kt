package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import kotlinx.coroutines.flow.Flow

interface GetGithubRepoDetailsUseCase {
    suspend fun getRepoDetails(repoId: String): Flow<BaseResponse<Repo>>
}