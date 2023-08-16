package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.MappedRepo
import kotlinx.coroutines.flow.Flow

interface FetchGithubRepoUseCase {
    fun execute(query: String): Flow<PagingData<MappedRepo>>
}