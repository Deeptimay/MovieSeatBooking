package com.example.testassignmentgitrepo.di

import com.example.testassignmentgitrepo.data.network.GithubApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(RetrofitModule::class)
class RetrofitModuleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Singleton
    lateinit var mockOkHttpClient: OkHttpClient

    @Inject
    lateinit var mockGithubApi: GithubApi

    @Named("baseUrl")
    @Inject
    lateinit var mockBaseUrl: String

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testOkHttpClientInjection() {
        // Verify that the OkHttp client is injected
        assert(mockOkHttpClient is OkHttpClient)
    }

    @Test
    fun testGithubApiInjection() {
        // Verify that the GithubApi is injected
        assert(mockGithubApi is GithubApi)
    }

    @Test
    fun testBaseUrlInjection() {
        // Verify that the base URL is injected
        assert(mockBaseUrl == "https://api.github.com/")
    }
}
