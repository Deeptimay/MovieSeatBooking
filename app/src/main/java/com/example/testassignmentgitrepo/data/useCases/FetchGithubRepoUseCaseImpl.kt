package com.example.testassignmentgitrepo.data.useCases

import androidx.paging.PagingData
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FetchGithubRepoUseCaseImpl @Inject constructor(private val gitHubRepoRepository: GitHubRepoRepository) :
    FetchGithubRepoUseCase {
    override fun execute(query: String): Flow<PagingData<MappedRepo>> {
        return gitHubRepoRepository.fetchAllTrendingGitHubRepo(query)
    }
}