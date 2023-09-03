package com.example.testassignmentgitrepo.di

import com.example.testassignmentgitrepo.data.repository.GitHubRepoRepositoryImpl
import com.example.testassignmentgitrepo.domain.repositoryAbstraction.GitHubRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesGithubRepository(impl: GitHubRepoRepositoryImpl): GitHubRepoRepository
}
