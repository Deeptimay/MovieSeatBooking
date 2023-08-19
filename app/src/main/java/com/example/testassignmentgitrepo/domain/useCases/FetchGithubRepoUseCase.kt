package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult

interface FetchGithubRepoUseCase {
    suspend operator fun invoke(query: String): NetworkResult<List<MappedRepo>>
}