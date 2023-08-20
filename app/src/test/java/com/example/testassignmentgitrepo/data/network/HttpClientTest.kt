package com.example.testassignmentgitrepo.data.network

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HttpClientTest {

    @Mock
    private lateinit var mockHttpLoggingInterceptor: HttpLoggingInterceptor

    @InjectMocks
    private lateinit var httpClient: HttpClient

    @Test
    fun `test setupOkhttpClient`() {

        // Call the method being tested
        val okHttpClient = httpClient.setupOkhttpClient(mockHttpLoggingInterceptor)

        // Verify that the interceptor is added
        assertEquals(1, okHttpClient.interceptors.size)

        // Verify that the interceptor is the same as the mock
        assertEquals(mockHttpLoggingInterceptor, okHttpClient.interceptors[0])

        // Verify the timeouts are set correctly
        assertEquals(60000, okHttpClient.connectTimeoutMillis)
        assertEquals(60000, okHttpClient.readTimeoutMillis)
    }
}
