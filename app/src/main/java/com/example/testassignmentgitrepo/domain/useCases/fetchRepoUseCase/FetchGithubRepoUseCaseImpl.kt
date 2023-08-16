package com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase

import androidx.paging.PagingData
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
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