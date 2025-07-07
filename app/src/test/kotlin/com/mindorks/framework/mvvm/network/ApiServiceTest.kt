package com.mindorks.framework.mvvm.network

import com.mindorks.framework.mvvm.data.network.ApiService
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test server login success`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "status_code": "success",
                    "user_id": 123,
                    "access_token": "test_token",
                    "user_name": "Test User",
                    "email": "test@example.com"
                }
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val request = LoginRequest.ServerLoginRequest("test@example.com", "password")
        val response = apiService.doServerLogin(request)

        assertTrue(response.isSuccessful)
        assertEquals("success", response.body()?.statusCode)
        assertEquals(123L, response.body()?.userId)
    }

    @Test
    fun `test server login failure`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody("""
                {
                    "status_code": "failed",
                    "message": "Invalid credentials"
                }
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val request = LoginRequest.ServerLoginRequest("test@example.com", "wrong_password")
        val response = apiService.doServerLogin(request)

        assertEquals(401, response.code())
        assertEquals(false, response.isSuccessful)
    }
}