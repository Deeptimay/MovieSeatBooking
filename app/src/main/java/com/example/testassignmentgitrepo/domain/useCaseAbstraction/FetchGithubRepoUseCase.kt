package com.example.testassignmentgitrepo.domain.useCaseAbstraction

import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.util.NetworkResult

interface FetchGithubRepoUseCase {
    suspend operator fun invoke(query: String): NetworkResult<List<MappedRepo>>
}