package com.midsummer.orchardjob.screens.loadingScreen

import android.util.Log
import androidx.lifecycle.*
import com.midsummer.orchardjob.data.OrchardJobRepository
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.screens.common.viewModel.SavedStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by nienle on 10,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class LoadingViewModel @Inject constructor(
    private val repository: OrchardJobRepository
) : SavedStateViewModel(){

    private lateinit var _jobs: MutableLiveData<List<OrchardJob>>
    val jobs : LiveData<List<OrchardJob>> get() = _jobs

    private lateinit var _configs: MutableLiveData<List<FieldConfig>>
    val configs : LiveData<List<FieldConfig>> get() = _configs

    private lateinit var _isLoading: MutableLiveData<Boolean>
    val isLoading : LiveData<Boolean> get() = _isLoading

    private lateinit var _isNotFound: MediatorLiveData<Boolean>
    val isNotFound : LiveData<Boolean> get() = _isNotFound

    private lateinit var handler: SavedStateHandle

    override fun init(handler: SavedStateHandle) {
        this.handler = handler
        _jobs = handler.getLiveData("jobs")
        _isLoading = handler.getLiveData("isLoading")
        _configs = handler.getLiveData("configs")

        _isNotFound = MediatorLiveData()
        _isNotFound.addSource(jobs) {
            _isNotFound.value = it.isNullOrEmpty() && isLoading.value == false
        }
        _isNotFound.addSource(configs) {
            _isNotFound.value = it.isNullOrEmpty() && isLoading.value == false
        }
        _isNotFound.addSource(isLoading) {
            _isNotFound.value = !it && jobs.value.isNullOrEmpty() && configs.value.isNullOrEmpty()
        }
        loadData()
    }


    fun loadData(){
        _isLoading.value = true
        viewModelScope.launch {
            val configs = repository.fetchAllFieldConfig()
            val data = repository.fetchData()
            _jobs.value = data
            _configs.value = configs
            _isLoading.value = false
        }
    }

    fun saveData(){
        handler["jobs"] = _jobs.value
        handler["configs"] = _configs.value
        handler["isLoading"] = _isLoading.value
    }
}