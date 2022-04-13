package com.midsummer.orchardjob


import com.midsummer.orchardjob.data.remote.GithubAPI
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by nienle on 13,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */


class OrchardJobRepositoryTest{

    private lateinit var githubAPI: GithubAPI
    private lateinit var server: MockWebServer


    @Before
    fun setUp() {
        server = MockWebServer()
        githubAPI = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubAPI::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun testRequestPath1() = runBlocking {
        enqueueMockResponse("testapi.json")
        githubAPI.getFieldConfig()
        val request = server.takeRequest()
        assertEquals(request.path, "/Hectre-Coding-Assignment/main/fieldConfig.json")
    }

    @Test
    fun testRequestPath2() = runBlocking {
        enqueueMockResponse("testJobs.json")
        githubAPI.getOrchardJobs()
        val request = server.takeRequest()
        assertEquals(request.path, "/Hectre-Coding-Assignment/main/api.json")
    }

    @Test
    fun testRequest() = runBlocking {
        enqueueMockResponse("testapi.json")
        val responseBody = githubAPI.getFieldConfig().body()
        assertEquals(responseBody?.size, 10)
    }
}