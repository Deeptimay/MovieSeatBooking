package com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.BaseResponse
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetGithubRepoDetailsUseCaseTest : GetGithubRepoDetailsUseCase {

    private val fakeFlow = MutableSharedFlow<BaseResponse<MappedRepo>>()

    suspend fun emit(value: BaseResponse<MappedRepo>) = fakeFlow.emit(value)

    override suspend fun execute(repoId: String): Flow<BaseResponse<MappedRepo>> {
        return fakeFlow
    }
}