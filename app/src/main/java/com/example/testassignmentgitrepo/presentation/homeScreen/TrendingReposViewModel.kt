package com.example.testassignmentgitrepo.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val SELECTED_REPO_ITEM = "selected_repo_item"

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val fetchGithubRepoUseCase: FetchGithubRepoUseCase,
) : ViewModel() {

    fun repoListStateFlowData() = repos

    private val repos: Flow<PagingData<MappedRepo>> =
        fetchGithubRepoUseCase.execute(DEFAULT_QUERY).cachedIn(viewModelScope)

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }
}