package com.midsummer.orchardjob.data.remote

import com.midsummer.orchardjob.pojo.OrchardJob
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
interface GithubAPI {

    @GET("Hectre-Coding-Assignment/main/api.json")
    suspend fun getOrchardJobs() : Response<List<OrchardJob>>

}