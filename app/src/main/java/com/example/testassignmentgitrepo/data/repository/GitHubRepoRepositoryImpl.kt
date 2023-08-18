package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.domain.models.MappedRepo
import com.example.testassignmentgitrepo.domain.repository.GitHubRepoRepository
import com.example.testassignmentgitrepo.domain.util.NetworkResult
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepoRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val repoMapper: RepoMapper
) :
    GitHubRepoRepository {

    override suspend fun fetchAllTrendingGitHubRepo(query: String): NetworkResult<List<MappedRepo>> {
        return try {
            val response = githubApi.getTrendingRepoList(query, 1, 100)
            if (response.isSuccessful) {
                val mappedDetails = response.body()?.let { repoMapper.fromEntityList(it.repo) }
                NetworkResult.ApiSuccess(mappedDetails!!)
            } else {
                NetworkResult.ApiError(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResult.ApiException(e)
        }
    }

    override suspend fun getGitHubRepoDetails(repoId: String): NetworkResult<MappedRepo> {
        return try {
            val response = githubApi.getRepoDetails(repoId)
            if (response.isSuccessful) {
                val mappedDetails = response.body()?.let { repoMapper.mapFromDomainModel(it) }
                NetworkResult.ApiSuccess(mappedDetails!!)
            } else {
                NetworkResult.ApiError(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResult.ApiException(e)
        }
    }
}