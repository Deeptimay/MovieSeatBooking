package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.BaseResponse
import kotlinx.coroutines.flow.Flow

interface GetGithubRepoDetailsUseCase {
    suspend fun execute(repoId: String): Flow<BaseResponse<MappedRepo>>
}