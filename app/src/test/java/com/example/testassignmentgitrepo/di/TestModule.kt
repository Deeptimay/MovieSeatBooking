package com.example.testassignmentgitrepo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    fun provideMockLoggingInterceptor(): HttpLoggingInterceptor {
        return mock(HttpLoggingInterceptor::class.java)
    }
}
