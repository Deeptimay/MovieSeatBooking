package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult

interface GetGithubRepoDetailsUseCase {
    suspend operator fun invoke(repoId: String): NetworkResult<MappedRepo>
}