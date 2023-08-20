package com.example.testassignmentgitrepo.domain.useCaseAbstraction

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult

interface GetGithubRepoDetailsUseCase {
    suspend operator fun invoke(repoId: String): NetworkResult<MappedRepo>
}