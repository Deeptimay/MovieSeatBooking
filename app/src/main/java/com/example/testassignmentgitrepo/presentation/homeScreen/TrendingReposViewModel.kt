package com.example.testassignmentgitrepo.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassignmentgitrepo.domain.useCasesImpl.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import com.example.testassignmentgitrepo.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReposViewModel @Inject constructor(
    private val fetchGithubRepoUseCase: FetchGithubRepoUseCase,
) : ViewModel() {

    private val _repoFlow = MutableStateFlow<UiState>(UiState.Loading)
    val repoFlow: StateFlow<UiState> = _repoFlow.asStateFlow()

    fun getRepoList() {
        viewModelScope.launch {
            val response = fetchGithubRepoUseCase(DEFAULT_QUERY)
            _repoFlow.update {
                when (response) {
                    is NetworkResult.ApiError -> UiState.Error()
                    is NetworkResult.ApiException -> UiState.Error()
                    is NetworkResult.ApiSuccess -> UiState.Success(response.data)
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "Q"
        const val SELECTED_REPO_ITEM = "selected_repo_item"
    }
}