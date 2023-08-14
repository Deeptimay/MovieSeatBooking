package com.example.testassignmentgitrepo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.domain.useCases.GitHubUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val gitHubUseCaseWrapper: GitHubUseCaseWrapper
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val repoDetailsMutableLiveData: MutableLiveData<Repo> = MutableLiveData<Repo>()

    fun getRepoDetailsMutableLiveData() = repoDetailsMutableLiveData

    val repos: LiveData<PagingData<Repo>> = currentQuery.switchMap { queryString ->
        gitHubUseCaseWrapper.fetchGithubRepoUseCase.getSearchResults(queryString)
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
            gitHubUseCaseWrapper.getGithubRepoDetailsUseCase.getRepoDetails(repoId)
                .collect() { it ->
                    it?.let { repo ->
                        repo.status?.let { repoStatus ->
                            if (repoStatus) {
                                repo.data?.let { repoData ->
                                    repoDetailsMutableLiveData.postValue(repoData)
                                }
                            }
                        }
                    }
                }
        }
    }
}