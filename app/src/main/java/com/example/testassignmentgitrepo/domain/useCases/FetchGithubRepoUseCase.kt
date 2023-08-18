package com.example.testassignmentgitrepo.domain.useCases

import androidx.paging.PagingData
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import kotlinx.coroutines.flow.Flow

interface FetchGithubRepoUseCase {
    fun execute(query: String): Flow<PagingData<MappedRepo>>
}