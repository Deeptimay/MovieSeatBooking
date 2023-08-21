package com.example.testassignmentgitrepo.presentation.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassignmentgitrepo.domain.useCasesImpl.GetGithubRepoDetailsUseCase
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
class RepoDetailsViewModel @Inject constructor(
    private val getGithubRepoDetailsUseCase: GetGithubRepoDetailsUseCase,
) : ViewModel() {

    private val _repoDetailsFlow = MutableStateFlow<UiState>(UiState.Loading)
    val repoDetailsFlow: StateFlow<UiState> = _repoDetailsFlow.asStateFlow()

    internal lateinit var repoId: String

    fun getRepoDetails(repoId: String) {
        this.repoId = repoId
        viewModelScope.launch {
            val response = getGithubRepoDetailsUseCase(repoId)
            _repoDetailsFlow.update {
                when (response) {
                    is NetworkResult.ApiError -> UiState.Error()
                    is NetworkResult.ApiException -> UiState.Error()
                    is NetworkResult.ApiSuccess -> UiState.Success(response.data)
                }
            }
        }
    }
}