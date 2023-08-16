package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.MappedRepo

interface FetchGithubRepoUseCase {
    fun execute(query: String): LiveData<PagingData<MappedRepo>>
}