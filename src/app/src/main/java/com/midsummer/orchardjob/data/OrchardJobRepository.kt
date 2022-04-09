package com.midsummer.orchardjob.data

import com.midsummer.orchardjob.data.local.OrchardJobDAO
import com.midsummer.orchardjob.data.remote.FetchOrchardJobUseCase
import com.midsummer.orchardjob.pojo.OrchardJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class OrchardJobRepository @Inject constructor(){

    @Inject lateinit var  remote: FetchOrchardJobUseCase
    @Inject lateinit var local: OrchardJobDAO


    suspend fun fetchData(fetchFromRemote: Boolean = true) : List<OrchardJob> {
        try{
            return if (fetchFromRemote){
                when(val result = remote.fetchOrchardJobs()){
                    is FetchOrchardJobUseCase.Result.Success -> {
                        local.insert(result.jobs)
                        local.getAllJobs()
                    }
                    is FetchOrchardJobUseCase.Result.Failure -> {
                        local.getAllJobs()
                    }
                }
            }else{
                local.getAllJobs()
            }
        }catch(t: Throwable){
            throw  t
        }
    }

    /*suspend fun fetchDataFromRemote(){
        withContext(Dispatchers.IO){
            try{
                val result = remote.fetchOrchardJobs()
                when(result){
                    is FetchOrchardJobUseCase.Result.Success -> {
                        local.insert(result.jobs)
                    }
                    is FetchOrchardJobUseCase.Result.Failure -> {

                    }
                }
            }catch(t: Throwable){
                throw t
            }
        }
    }

    suspend fun fetchDataFromLocal() : List<OrchardJob>{
        try {
            return local.getAllJobs()
        } catch (t: Throwable) {
            throw t
        }
    }*/
}