package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult

interface GetGithubRepoDetailsUseCase {
    suspend fun execute(repoId: String): NetworkResult<MappedRepo>
}