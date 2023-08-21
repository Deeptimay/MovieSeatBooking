package com.example.testassignmentgitrepo.data.repository

import com.example.testassignmentgitrepo.data.mappers.RepoMapper
import com.example.testassignmentgitrepo.data.models.MappedRepo
import com.example.testassignmentgitrepo.data.network.GithubApi
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
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
            val repoListResponse = githubApi.getTrendingRepoList(query, 1, 100)
            if (repoListResponse.isSuccessful) {
                val mappedRepoList =
                    repoListResponse.body()?.let { repoMapper.fromEntityList(it.repo) }
                mappedRepoList?.let { NetworkResult.ApiSuccess(it) } ?: kotlin.run {
                    NetworkResult.ApiError(
                        code = repoListResponse.code(),
                        message = "data mapping error " + repoListResponse.body()
                    )
                }
            } else {
                NetworkResult.ApiError(
                    code = repoListResponse.code(),
                    message = repoListResponse.errorBody().toString()
                )
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResult.ApiException(e)
        }
    }

    override suspend fun getGitHubRepoDetails(repoId: String): NetworkResult<MappedRepo> {
        return try {
            val repoDetailResponse = githubApi.getRepoDetails(repoId)
            if (repoDetailResponse.isSuccessful) {
                val mappedDetails =
                    repoDetailResponse.body()?.let { repoMapper.mapRepoToMappedRepoModel(it) }
                mappedDetails?.let { NetworkResult.ApiSuccess(it) } ?: kotlin.run {
                    NetworkResult.ApiError(
                        code = repoDetailResponse.code(),
                        message = "data mapping error " + repoDetailResponse.body()
                    )
                }
            } else {
                NetworkResult.ApiError(
                    code = repoDetailResponse.code(),
                    message = repoDetailResponse.errorBody().toString()
                )
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResult.ApiException(e)
        }
    }
}