package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.Repo

interface FetchGithubRepoUseCase {
    fun getSearchResults(query: String): LiveData<PagingData<Repo>>
}