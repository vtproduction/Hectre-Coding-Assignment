package com.midsummer.orchardjob.data.remote

import com.midsummer.orchardjob.pojo.OrchardJob
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class FetchOrchardJobUseCase @Inject constructor(private val githubAPI: GithubAPI) {

    sealed class Result{
        data class Success(val jobs : List<OrchardJob>) : Result()
        object Failure: Result()
    }

    suspend fun fetchOrchardJobs() : Result {
        return withContext(Dispatchers.IO){
            try{
                val response = githubAPI.getOrchardJobs()
                if (response.isSuccessful && response.body() != null){
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }
            }catch(t: Throwable){
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw t
                }
            }
        }
    }
}