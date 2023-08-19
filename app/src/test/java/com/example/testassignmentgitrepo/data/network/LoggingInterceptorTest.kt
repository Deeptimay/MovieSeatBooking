package com.example.testassignmentgitrepo.data.network

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Test

class LoggingInterceptorTest {
    @Test
    fun `test create returns interceptor with BODY level`() {
        val interceptor = LoggingInterceptor.create()

        assertEquals(HttpLoggingInterceptor.Level.BODY, interceptor.level)
    }
}
