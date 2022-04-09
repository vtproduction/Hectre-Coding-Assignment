package com.midsummer.orchardjob.screens.common.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by nienle on 10,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
abstract class SavedStateViewModel :ViewModel(){

    abstract fun init(handler: SavedStateHandle)
}