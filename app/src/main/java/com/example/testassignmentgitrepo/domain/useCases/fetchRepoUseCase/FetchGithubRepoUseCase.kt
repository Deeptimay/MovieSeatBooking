package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.model.Repo

interface FetchGithubRepoUseCase {
    suspend fun getSearchResults(query: String): LiveData<PagingData<Repo>>
}