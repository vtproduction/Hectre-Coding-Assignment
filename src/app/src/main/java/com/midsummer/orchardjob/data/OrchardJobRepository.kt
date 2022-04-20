package com.midsummer.orchardjob.data

import com.midsummer.orchardjob.data.local.OrchardJobDAO
import com.midsummer.orchardjob.data.remote.FetchFieldConfigUseCase
import com.midsummer.orchardjob.data.remote.FetchOrchardJobUseCase
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class OrchardJobRepository @Inject constructor(){

    @Inject lateinit var orchardUseCase: FetchOrchardJobUseCase
    @Inject lateinit var fieldConfigUseCase: FetchFieldConfigUseCase
    @Inject lateinit var local: OrchardJobDAO


    suspend fun fetchData(fetchFromRemote: Boolean = true) : List<OrchardJob> {
        try{
            return if (fetchFromRemote){
                when(val result = orchardUseCase.fetchOrchardJobs()){
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

    suspend fun fetchAllFieldConfig(fetchFromRemote: Boolean = true) : List<FieldConfig> {
        try{
            return if(fetchFromRemote){
                when(val result = fieldConfigUseCase.fetchFieldConfigs()){
                    is FetchFieldConfigUseCase.Result.Success -> {
                        local.insertFieldConfig(result.fieldConfig)
                        result.fieldConfig
                    }
                    is FetchFieldConfigUseCase.Result.Failure -> {
                        local.getAllFieldConfig()
                    }
                }
            }else{
                local.getAllFieldConfig()
            }
        }catch(t: Throwable){
            throw  t
        }
    }

    suspend fun fetchFieldConfig(rowId: Int) : FieldConfig? {
        try {
            return local.getRow(rowId)
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun fetchLocalFieldConfigs() : List<FieldConfig> {
        try {
            return local.getAllFieldConfig()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun fetchLocalPruningJobs() : List<OrchardJob> {
        try {
            return local.getPruningJobs()
        } catch (t: Throwable) {
            throw t
        }
    }

    suspend fun fetchLocalThinningJobs() : List<OrchardJob> {
        try {
            return local.getThinningJobs()
        } catch (t: Throwable) {
            throw t
        }
    }

}