package com.example.testassignmentgitrepo.presentation.ui

import com.example.testassignmentgitrepo.domain.util.ErrorTypes

sealed interface UiState {
    data object Loading : UiState
    data class Success<T>(val content: T) : UiState
    data class Error(val throwable: ErrorTypes? = null) : UiState
}
