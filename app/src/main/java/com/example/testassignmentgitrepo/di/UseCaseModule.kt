package com.example.testassignmentgitrepo.di

import com.example.testassignmentgitrepo.domain.useCases.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.data.useCases.FetchGithubRepoUseCaseImpl
import com.example.testassignmentgitrepo.domain.useCases.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.data.useCases.GetGithubRepoDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun fetchGithubRepoUseCase(useCaseImpl: FetchGithubRepoUseCaseImpl): FetchGithubRepoUseCase

    @Binds
    @Singleton
    internal abstract fun getGithubRepoDetailsUseCaseImpl(useCaseImpl: GetGithubRepoDetailsUseCaseImpl): GetGithubRepoDetailsUseCase
}