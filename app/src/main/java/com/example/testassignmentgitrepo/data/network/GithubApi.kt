package com.example.testassignmentgitrepo.data.network

import com.example.testassignmentgitrepo.data.models.Repo
import com.example.testassignmentgitrepo.data.models.TrendingRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer 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",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepoList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<TrendingRepoResponse>

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer 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",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("repositories/{repo_id}")
    suspend fun getRepoDetails(
        @Path("repo_id") repoId: String
    ): Response<Repo>
}