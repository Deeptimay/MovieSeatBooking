package com.example.testassignmentgitrepo.presentation.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassignmentgitrepo.data.network.BaseResponse
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.util.UiState
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
    private val repoDetailsFlow: StateFlow<UiState> = _repoDetailsFlow.asStateFlow()

    internal lateinit var repoId: String

    fun repoDetailsStateFlowData() = repoDetailsFlow

    fun getRepoDetails(repoId: String) {
        this.repoId = repoId
        viewModelScope.launch {
            getGithubRepoDetailsUseCase.execute(repoId)
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
}