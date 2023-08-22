package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepoRepositoryImpl @Inject constructor(
    private val baseRepository: BaseRepository,
    private val githubApi: GithubApi,
    private val repoMapper: RepoMapper
) :
    GitHubRepoRepository {

    override suspend fun fetchAllTrendingGitHubRepo(query: String): NetworkResult<List<MappedRepo>> {
        return baseRepository.performApiCall {
            githubApi.getTrendingRepoList(query, 1, 100)
        }.mapSuccess {
            repoMapper.fromEntityList(it.repo)
        } as NetworkResult<List<MappedRepo>>
    }

    override suspend fun getGitHubRepoDetails(repoId: String): NetworkResult<MappedRepo> {
        return baseRepository.performApiCall {
            githubApi.getRepoDetails(repoId)
        }.mapSuccess {
            repoMapper.mapRepoToMappedRepoModel(it)
        } as NetworkResult<MappedRepo>
    }

    private inline fun <T : Any, R : Any> NetworkResult<T>.mapSuccess(transform: (T) -> R): NetworkResult<out Any> {
        return when (this) {
            is NetworkResult.ApiSuccess -> NetworkResult.ApiSuccess(transform(data))
            is NetworkResult.ApiError -> this
            is NetworkResult.ApiException -> this
            else -> this
        }
    }
}