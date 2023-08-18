package com.example.testassignmentgitrepo.presentation.util

sealed interface UiState {
    object Loading : UiState
    data class Success<T>(val content: T) : UiState
    data class Error(val throwable: Throwable? = null) : UiState
}
