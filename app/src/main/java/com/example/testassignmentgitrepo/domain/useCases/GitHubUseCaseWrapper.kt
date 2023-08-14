package com.example.testassignmentgitrepo.domain.useCases

import com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.GetGithubRepoDetailsUseCase

data class GitHubUseCaseWrapper(
    val fetchGithubRepoUseCase: FetchGithubRepoUseCase,
    val getGithubRepoDetailsUseCase: GetGithubRepoDetailsUseCase
)