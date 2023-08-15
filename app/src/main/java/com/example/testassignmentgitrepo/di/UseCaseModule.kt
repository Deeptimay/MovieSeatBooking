package com.example.testassignmentgitrepo.di

import com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase.FetchGithubRepoUseCase
import com.example.testassignmentgitrepo.domain.useCases.fetchRepoUseCase.FetchGithubRepoUseCaseImpl
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.GetGithubRepoDetailsUseCase
import com.example.testassignmentgitrepo.domain.useCases.getRepoDetailsUseCase.GetGithubRepoDetailsUseCaseImpl
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