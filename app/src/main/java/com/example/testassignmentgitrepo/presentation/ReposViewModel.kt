package com.example.testassignmentgitrepo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.useCases.GitHubUseCaseWrapper
import com.example.testassignmentgitrepo.retrofitSetup.BaseResponse
import com.example.testassignmentgitrepo.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SELECTED_REPO_ITEM = "selected_repo_item"

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val gitHubUseCaseWrapper: GitHubUseCaseWrapper,
) : ViewModel() {

    private val _repoDetailsFlow = MutableStateFlow<UiState>(UiState.Loading)
    val repoDetailsFlow: StateFlow<UiState> = _repoDetailsFlow.asStateFlow()

    fun repoDetailsStateFlowData() = repoDetailsFlow
    fun repoListStateFlowData() = repos

    val repos: Flow<PagingData<MappedRepo>> =
        gitHubUseCaseWrapper.fetchGithubRepoUseCase.execute(DEFAULT_QUERY).cachedIn(viewModelScope)

    fun getRepoDetails(repoId: String) {
        viewModelScope.launch {
            gitHubUseCaseWrapper.getGithubRepoDetailsUseCase.execute(repoId)
                .collect { result ->
                    _repoDetailsFlow.update {
                        when (result) {
                            is BaseResponse.Loading -> UiState.Loading
                            is BaseResponse.Success -> UiState.Success(result.data!!)
                            is BaseResponse.Error -> UiState.Error()
                        }
                    }
                }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
    }
}