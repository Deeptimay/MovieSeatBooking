package com.example.testassignmentgitrepo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.domain.useCases.GitHubUseCaseWrapper
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SELECTED_REPO_ITEM = "selected_repo_item"

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val gitHubUseCaseWrapper: GitHubUseCaseWrapper,
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val repoDetailsMutableLiveData: MutableLiveData<BaseResponse<MappedRepo>> =
        MutableLiveData<BaseResponse<MappedRepo>>()

    fun getRepoDetailsMutableLiveData() = repoDetailsMutableLiveData

    val repos: LiveData<PagingData<MappedRepo>> = currentQuery.switchMap { queryString ->
        gitHubUseCaseWrapper.fetchGithubRepoUseCase.execute(queryString)
    }.cachedIn(viewModelScope)

    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    fun searchReposLocal(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }

    fun getRepoDetails(repoId: String) {
        viewModelScope.launch {
            gitHubUseCaseWrapper.getGithubRepoDetailsUseCase.execute(repoId)
                .collect { it ->
                    repoDetailsMutableLiveData.postValue(it)
                }
        }
    }
}