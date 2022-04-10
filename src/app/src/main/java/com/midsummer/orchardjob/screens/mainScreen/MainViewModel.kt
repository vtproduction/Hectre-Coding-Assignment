package com.midsummer.orchardjob.screens.mainScreen

import androidx.lifecycle.SavedStateHandle
import com.midsummer.orchardjob.data.OrchardJobRepository
import com.midsummer.orchardjob.screens.common.viewModel.SavedStateViewModel
import javax.inject.Inject

/**
 * Created by nienle on 10,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class MainViewModel @Inject constructor(
    private val repository: OrchardJobRepository
) : SavedStateViewModel() {


    override fun init(handler: SavedStateHandle) {

    }
}